package com.sangam.sangam.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Data;

@Data
public class LogDTO implements Comparable<LogDTO> {
	public String logId; // Log id
	
	private String logDescription;
	
	public Integer taskStatusId;
	
	public String taskStatus;
	
	public Integer segmentId;
	
	private String id; 	// log_id from log table	
	
	private UserTeamDTO pByUser = new UserTeamDTO(); 	// Create by user details 
	
	private UserTeamDTO pToUser = new UserTeamDTO(); 	//  Task assigned to this team 
	
	private LocalDateTime time;
	private String pLogTime;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LogDTO))
			return false;
		LogDTO other = (LogDTO) obj;
		return Objects.equals(logId, other.logId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(logId);
	}

	@Override
	public int compareTo(LogDTO logDTO) {
		return this.time.compareTo(logDTO.getTime());
	}
}
