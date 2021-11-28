package com.sangam.sangam.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangam.sangam.dto.FilterTaskDTO;
import com.sangam.sangam.dto.LogDTO;
import com.sangam.sangam.dto.TaskDTO;
import com.sangam.sangam.dto.TeamAdminDTO;
import com.sangam.sangam.dto.UserTeamRolePrivilegeDTO;
import com.sangam.sangam.dto.UtilXLDataDTO;
import com.sangam.sangam.dto.ViewStatusMessage;
import com.sangam.sangam.utils.Constants;
import com.sangam.sangam.utils.InternalTaskService;
import com.sangam.sangam.utils.InternalTeamService;
import com.sangam.sangam.utils.InternalUtilService;
import com.sangam.sangam.web.security.UserPrincipal;
import com.sangam.sangam.web.security.repository.UserRepository;

import lombok.Data;

/*
 * This bean will get instantiated for all task related functions
 * http://localhost:8080/task/taskDetails?flag=view&taskId=1
 * http://localhost:8080/task/taskDetails?flag=create
 * http://localhost:8080/task/taskDetails?flag=view&userId=1
 * http://localhost:8080/task/taskDetails?flag=view&pageId=1
 * */

@Component
@ViewScoped
@Data
public class TaskBean {
	
	@Autowired
	InternalTaskService taskService;
	
	@Autowired
	InternalTeamService teamService;
	
	@Autowired
	InternalUtilService utilService;
	
	private String 	mapUrlParamTaskId;
	private String 	mapUrlParamUserId;
	private String 	mapUrlParamTeamId;
	private String 	mapUrlParamView;
	private String 	pageName;
	private String 	myUserId;
	
	//private TaskDTO readTaskDetails = new TaskDTO();
	private Set<TaskDTO> 	readSetTasks = new TreeSet<TaskDTO>();
	private LogDTO 			mapLogDTO = new LogDTO();
	
	private String 	readStatusInd;
	private String 	readUpdationStatus;
	private LogDTO 	taskLogUpdateDetails = new LogDTO();
	
	private List<TeamAdminDTO> 			readListTeamMembers = new ArrayList<TeamAdminDTO>();
	private UserTeamRolePrivilegeDTO 	readMyTeamRolePrivilege = new UserTeamRolePrivilegeDTO();
	List<UserTeamRolePrivilegeDTO> 		listUserTeamPrivileges = new ArrayList<UserTeamRolePrivilegeDTO>();
	
	private Integer myRoleId;
	private Integer pageId;  
	private String 	readTeamId;
	private String 	readTaskId;
	
	private Integer readSelectedItem;
	private String	readIntendedId; // Maps team id or user id based on selection in dropdown
	
	private TaskDTO readTaskDTO = new TaskDTO();
	private TaskDTO mapTaskDTO = new TaskDTO();
	private LogDTO 	mapTaskLog =  new LogDTO();
	
	private List<SelectItem> mapTeamMembers = new ArrayList<SelectItem>();
	private List<SelectItem> mapListTeam 	= new ArrayList<SelectItem>();
	private ViewStatusMessage readViewStatusMessage = new ViewStatusMessage();
	
	// Below is used by filtering tasks
	private String 	mapFilterTaskId = "";
	private String 	mapFilterTaskTitle = "";
	private String 	mapFilterTaskDesc = "";
	private String 	mapFilterCreatedByTeamId = "";
	private String 	mapFilterCreatedByUserId = "";
	List<SelectItem> readFilterlistTaskStatus = null;
	private String 	mapFilterTaskStatus = "";
	private Date 	mapFilterAfterDate;
	private Date 	mapFilterBeforeDate;
	
	private String mapTaskUtils;
	private String readPageTitle;
	
	@Autowired
	UserRepository userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(TaskBean.class);
	
	public List<SelectItem> getSelectItem(Object obj) {
		try {
			if(obj == null)
				return null;
			ObjectMapper objectMapper  = new ObjectMapper();
			return objectMapper.readValue("["+obj+"]",new TypeReference<List<SelectItem>>(){});
		}catch(Exception e) {
			logger.error("TaskBean Error while forming select items");
			return null;
		}
	}
	
	public void populateTeamRolePrivilege()  throws Exception{
		List<Object[]> objRolePrivilege = userRepository.findMyTeamsRoleWithPrivilege(this.myUserId);
		try {
		objRolePrivilege.forEach( e -> { UserTeamRolePrivilegeDTO obj = new UserTeamRolePrivilegeDTO(
																								Constants.getString(e[0]), 
																								Constants.getString(e[1]), 
																								Constants.getString(e[2]), 
																								Constants.getInteger(e[3]),
																								Constants.getString(e[4]),
																								this.getSelectItem(e[5])
																								);
										
										this.listUserTeamPrivileges.add(obj);
										});
		}catch(Exception e) {
			
		}
	}
	
	public void fetchTaskDetails(String flag) throws Exception{
		this.readStatusInd = Constants.STATIC_READ_STATUS_IND_TASK;
		Map<String,String> mapParams = this.loadParams(flag);
		this.readSetTasks.clear();
		
		// flag = 1, My tasks with required status
		// flag = 2, My team tasks with required status
		// flag = 3, My Queue tasks with required status
		// flag = 4, Specific task with required status
		
		this.readSetTasks.addAll(taskService.fetchTaskDetails(mapParams));
		this.readSetTasks.forEach( e -> { e.process(); });
	}
	
	public Map<String,String> loadParams(String flag) throws Exception{
		Map<String,String> mapParams = new HashMap<String,String>();
			mapParams.put(Constants.STATIC_TASK_PARAM_USER_ID, this.myUserId);
		if(this.mapUrlParamTaskId != null)
			mapParams.put(Constants.STATIC_TASK_PARAM_ID, this.mapUrlParamTaskId.toString());
		if(this.mapUrlParamUserId != null)
			mapParams.put(Constants.STATIC_TASK_PARAM_ID, this.mapUrlParamUserId.toString());
		if(this.mapUrlParamTeamId != null)
			mapParams.put(Constants.STATIC_TASK_PARAM_ID, this.mapUrlParamTeamId.toString());
		if(flag != null)
			mapParams.put(Constants.STATIC_TASK_PARAM_FLAG,flag);
		return mapParams;
	}

	 protected void getCurrentUser() throws Exception{
	        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	        if (!(loggedInUser instanceof AnonymousAuthenticationToken)) {
		        UserPrincipal userDetails = (UserPrincipal)loggedInUser.getPrincipal();
		        this.myUserId = userDetails.getId();
	        }
	    }
	
	public void loadMyTeamRolePrivilege()  throws Exception{
		this.readMyTeamRolePrivilege = new UserTeamRolePrivilegeDTO();
		if(!listUserTeamPrivileges.isEmpty() && this.readTeamId != null) {
			Optional<UserTeamRolePrivilegeDTO> tempObj = listUserTeamPrivileges.stream().filter( e -> e.getTeamId().equalsIgnoreCase(this.readTeamId)).findAny();
			if(tempObj.isPresent()) {
				this.readMyTeamRolePrivilege = tempObj.get();
				this.cleanRolePrivileges();
			}
		}
	}
	
	public void loadTeamMembers(String teamId) throws Exception{ 
		this.mapTeamMembers = getSelectItem(userRepository.fetchTeamsMembers(teamId));
	}
	
	public void loadTeamsList() throws Exception{
		this.mapListTeam = getSelectItem(userRepository.fetchTeamsList());
	}
	
	public void loadSelectedItems() throws Exception{
		if(this.readSelectedItem.intValue() == Constants.IND_LOAD_TEAM_LIST.intValue())
			this.loadTeamsList();
		if(this.readSelectedItem.intValue() == Constants.IND_LOAD_TEAM_MEMBERS.intValue())
			this.loadTeamMembers(this.readTeamId);
	}
	
	public void cleanRolePrivileges() throws Exception{
		if(!this.readMyTeamRolePrivilege.getListRolePrivilege().isEmpty())
			this.readMyTeamRolePrivilege.getListRolePrivilege().removeIf( e -> e.getValue().toString().equals(Constants.IND_TASK_CREATED.toString()));
	}
	
	public void clearStatusMessage()  throws Exception{
		this.readViewStatusMessage = new ViewStatusMessage();
	}
	
	public void mapValuesFromURL() throws Exception{
			this.readUpdationStatus = "";
			if(this.myUserId==null)
				this.getCurrentUser();
			
			if(listUserTeamPrivileges.isEmpty())
				this.populateTeamRolePrivilege();
			if(this.mapUrlParamTeamId != null)
				this.readTeamId = this.mapUrlParamTeamId;
			this.clearStatusMessage();
			
			switch(this.mapUrlParamView) {
				case Constants.URL_PARAM_TASK_DETAIL:	if(mapUrlParamTaskId != null)
															this.fetchTaskDetails(Constants.STATIC_TASK_PARAM_SPECIFIC_TASK);
														if(this.readSetTasks != null) {
															this.readTaskDTO =  this.readSetTasks.iterator().next();
															 Comparator<LogDTO> sortByTime = (LogDTO o1, LogDTO o2)->o2.getTime().compareTo(o1.getTime());
															 TreeSet<LogDTO> tempDTO = new TreeSet(sortByTime);
															 tempDTO.addAll(this.readTaskDTO.getLog());
															 this.readTaskDTO.setLog(tempDTO);
															this.readTeamId =  this.readTaskDTO.getPCreatedByTeam().getTeamId();
															this.readPageTitle = this.readTaskDTO.getTitle();
														}
														this.readTaskId = this.mapUrlParamTaskId;
														this.loadMyTeamRolePrivilege();
														break;
				
				case Constants.URL_PARAM_TASK_UTILS:	this.readPageTitle = Constants.PAGE_TITLE_TASK_UTILS;
														break;
				
				case Constants.URL_PARAM_TEAM_TASKS:	this.fetchTaskDetails(Constants.STATIC_TASK_PARAM_MY_TEAM_TASK);
														this.readPageTitle = Constants.PAGE_TITLE_TEAM_TASK;
														this.loadMyTeamRolePrivilege();
														break;
														
				case Constants.URL_PARAM_MY_TASK:		this.fetchTaskDetails(Constants.STATIC_TASK_PARAM_MY_TASK);
														this.readPageTitle = Constants.PAGE_TITLE_MY_TASK;
														break;
					
				case Constants.URL_PARAM_QUEUE_TASKS:	this.fetchTaskDetails(Constants.STATIC_TASK_PARAM_MY_QUEUE_TASK);
														this.readPageTitle = Constants.PAGE_TITLE_QUEUE;
														break;
				
				case Constants.URL_PARAM_CREATE_TASK:	this.loadMyTeamRolePrivilege();
														this.readPageTitle = Constants.PAGE_TITLE_CREATE_TASK;
														break;
														
				case Constants.URL_PARAM_FILTER_TASK:	if(this.readFilterlistTaskStatus == null)
															this.readFilterlistTaskStatus = this.getSelectItem(userRepository.fetchTaskStatus());
														this.readPageTitle = Constants.PAGE_TITLE_FILTER_TASK;
														break;
			}
			this.clearPrams();
			return;
	}
	
	public void clearPrams() throws Exception{
		this.mapUrlParamUserId = null;
		this.mapUrlParamTeamId = null;
		this.mapUrlParamTaskId = null;
	}
	
	public String createNewTask() throws Exception{
			mapTaskLog.getPByUser().setUserId(this.myUserId);
			mapTaskLog.getPToUser().setUserId(this.readTeamId);
			mapTaskLog.setTaskStatusId(Constants.TASK_CREATE_INDICATOR);
			mapTaskLog.setSegmentId(1);
			mapTaskLog.setTime(LocalDateTime.now());
			mapTaskLog.setLogDescription("Created");
			mapTaskDTO.getInputLog().add(mapTaskLog);
			
			TaskDTO taskDTO = taskService.updateTask(mapTaskDTO);
			readViewStatusMessage = new ViewStatusMessage(taskDTO.getTaskId(),"Task has been created successfully");
			loadFacesMessage(readViewStatusMessage);
			if(readViewStatusMessage.getId() != null) {
				this.mapUrlParamTaskId = this.readTaskId;
				this.loadParams("4");
				this.mapValuesFromURL();
			}
		return null;
	}

	public void saveLog() throws Exception{
			mapLogDTO.setId(this.readTaskDTO.getTaskId());
			mapLogDTO.getPByUser().setUserId(this.myUserId);
			
			if(this.readSelectedItem == Constants.IND_TASK_CREATED || this.readSelectedItem == Constants.IND_TASK_QUEUED)
				mapLogDTO.getPToUser().setUserId( this.readIntendedId);
			else
				mapLogDTO.getPToUser().setUserId( this.myUserId);
			
			mapLogDTO.setSegmentId(1);
			mapLogDTO.setTaskStatusId(this.readSelectedItem);
			LogDTO logDTO = taskService.updateTaskLog(mapLogDTO);
			readViewStatusMessage = new ViewStatusMessage(logDTO.getLogId(),"Task Log has been created successfully");
			loadFacesMessage(readViewStatusMessage);
			this.mapUrlParamTaskId = this.readTaskDTO.getTaskId();
			this.loadParams(Constants.STATIC_TASK_PARAM_SPECIFIC_TASK);
			this.mapValuesFromURL();
	}
	
	public void loadFacesMessage(ViewStatusMessage vsm) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful",  vsm.getStatusMessage()));
	}
	
	public void filterTasks() throws Exception {
		FilterTaskDTO filterTask = new FilterTaskDTO();
		filterTask.setListTaskId(this.mapFilterTaskId.equals("")?null:this.mapFilterTaskId);
		filterTask.setTaskTitle(this.mapFilterTaskTitle.equals("")?null:this.mapFilterTaskTitle);
		filterTask.setTaskDesc(this.mapFilterTaskDesc.equals("")?null:this.mapFilterTaskDesc);
		filterTask.setTaskStatus((this.mapFilterTaskStatus.equals(""))?null:this.mapFilterTaskStatus);
		filterTask.setCreatedByTeamId(this.mapFilterCreatedByTeamId.equals("")?null:this.mapFilterCreatedByTeamId);
		//filterTask.setCreatedByUserId(createdByUserId);
		filterTask.setCreatedBefore((this.mapFilterBeforeDate == null)?"":this.mapFilterBeforeDate.toString());
		filterTask.setCreatedAfter((this.mapFilterAfterDate== null)?"":this.mapFilterAfterDate.toString());
		
		this.readSetTasks.clear();
		this.readSetTasks.addAll(taskService.fetchFilterTask(filterTask));
		System.out.println(" Size "+this.readSetTasks.size());
	}
	
	public void manageTasks() throws Exception{
		if(this.mapTaskUtils !=null ) {
			UtilXLDataDTO util = new UtilXLDataDTO();
			util = new ObjectMapper().readValue(this.mapTaskUtils, UtilXLDataDTO.class);
			util.setUtilUserId(this.myUserId);
			if(util.getUtilKey().equalsIgnoreCase(Constants.UTILS_CREATE_TASK))
				this.mapTaskUtils = utilService.manageTasks(util);
			else if(util.getUtilKey().equalsIgnoreCase(Constants.UTILS_UPDATE_TASK))
				this.mapTaskUtils = utilService.manageTasks(util);
			else if(util.getUtilKey().equalsIgnoreCase(Constants.UTILS_FILTER_TASK))
				this.mapTaskUtils = utilService.fetchTaskReport(util);
			else if(util.getUtilKey().equalsIgnoreCase(Constants.UTILS_LOAD_STATICS))
				this.mapTaskUtils = utilService.fetchStatics(util);
		}
		return;
	}
}
