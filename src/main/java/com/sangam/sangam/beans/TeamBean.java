package com.sangam.sangam.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sangam.sangam.dto.TeamDTO;
import com.sangam.sangam.utils.InternalTeamService;
import com.sangam.sangam.web.security.repository.UserRepository;

import lombok.Data;


/*
 * This bean will get instantiated for all page related functions. Below are sample URLs
 */

@Component
@ViewScoped
@Data
public class TeamBean {
	
	@Autowired
	InternalTeamService teamService;
	
	private String 	mapUrlParamTeamId;
	private String 	mapUrlParamUserId;
	private String 	mapUrlParamTeamName;
	private String 	mapUrlParamView;
	private Set<TeamDTO> readSetTeams = new TreeSet<TeamDTO>();
	private TeamDTO readTeam = new TeamDTO();

	private static final Logger logger = LoggerFactory.getLogger(TeamBean.class);
	
	@Autowired
	UserRepository userRepository;
	
	@PostConstruct
	public void loadUserDetails() {
		
		//Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        //UserPrincipal userDetails = (UserPrincipal)loggedInUser.getPrincipal();
        //this.myUserId = (int)(long)userDetails.getId();
        //this.myUserId = (long)5;
	}
	
	public void fetchTeamDetails(String flag) {
		Map<String,String> mapParams = this.loadParams(flag);
		this.readSetTeams = teamService.fetchTeamDetails(mapParams);
		if(flag.equals("2"))
			this.readTeam = this.readSetTeams.iterator().next();
		else
			this.readTeam.setSummary("Team Members");
	}
	
	public Map<String,String> loadParams(String flag){
		Map<String,String> mapParams = new HashMap<String,String>();
		if(this.mapUrlParamUserId != null)
			mapParams.put("userId", this.mapUrlParamUserId.toString());
		if(this.mapUrlParamTeamId != null)
			mapParams.put("teamId", this.mapUrlParamTeamId.toString());
		if(flag != null)
			mapParams.put("flag",flag);
		return mapParams;
	}
	
	public void mapValuesFromURL() throws Exception{
		// team?view=myTeams&id=userId
		// team?view=detailedInfo&id=teamId
		logger.error(" Entered ...... ");
		switch(this.mapUrlParamView) {
			case "myTeams":{
				this.fetchTeamDetails("1");
				break;
			}
			case "detailedInfo":{
				this.fetchTeamDetails("2");
				break;
			}
		}
		this.clearPrams();
		return;
	}
	
	public void clearPrams() {
		this.mapUrlParamUserId = null;
		this.mapUrlParamTeamId = null;
		this.mapUrlParamTeamName = null;
	}
	
	public void goToTasks() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			FacesContext context = FacesContext.getCurrentInstance();
//			context.getExternalContext().redirect("../task/taskDetails?flag=view&pageId=" + this.pageDTO.getPageId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
