package com.sangam.sangam.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// This class is created only to eliminate difficulty with VBA coding to get JSON data
public class UtilTaskDTO {
	public Integer 	slNo;
	public String 	taskId;
	public String 	createdbyUser;
	public String 	createdByTeam;
	public String 	queuedToTeam;
	public String 	assignedTo;
	public String 	latestStatus;
	public String 	comment;
	public String 	taskTitle;
	public String 	taskDetails;
	public Integer 	effortHrs;

	public String 	myUserId;
	public String 	myRoles;
	public String 	listMyTaskPrivilege;
	
	public String 	idTeamQueueOrTeam;
	
}
