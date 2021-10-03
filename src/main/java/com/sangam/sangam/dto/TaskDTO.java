package com.sangam.sangam.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.faces.model.SelectItem;


import lombok.Data;

@Data
public class TaskDTO implements Comparable<TaskDTO>{
	private String taskId;
	private String title; 
	private String description; 
	private String effortHrs;
	private Integer slNo;
	private TreeSet<LogDTO> 	log 		= new TreeSet<LogDTO>();
	private ArrayList<LogDTO> 	inputLog 	= new ArrayList<LogDTO>(); // Used to read values from request json 
	
	private UserTeamDTO pCreatedByUser; 	// Task created by this user
	private UserTeamDTO pCreatedByTeam; 	// Task created by this team
	private UserTeamDTO pQueuedToTeam; 		// Task added to this team's queue
	private UserTeamDTO pAssignedToUser; 	// Task assigned to this team
	private LogDTO 		pLatestLog;			// Current status of the task
	private LocalDateTime pTime; 			// Taken from latest log and used for sorting
	
	public Boolean pIsClosed 		= false;
	public Boolean pIsAssignedToMe 	= false;
	public Boolean pIsCreatedByMe 	= false;
	public Boolean pIsTeamTask		= false;
	public Boolean pIsQueueTask		= false;
	
	private Map<String,String> listMyTaskPrivilege = new TreeMap<String, String>();
	private List<SelectItem> listMyPrivilege = new ArrayList<SelectItem>();

	@Override
	public int compareTo(TaskDTO taskDTO) {
		return taskDTO.getTaskId().compareTo(this.getTaskId());
	}
	
	public Set<LogDTO> getLogs(){
		return this.log;
	}

	public void process() {
		for(Map.Entry<String,String> entry  :listMyTaskPrivilege.entrySet()) {
			SelectItem selectItem = new SelectItem();
			selectItem.setLabel(entry.getValue());
			selectItem.setValue(entry.getKey());
			this.listMyPrivilege.add(selectItem);
		}
	}
}
