package com.sangam.sangam.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.sangam.sangam.utils.FunctionsUtils;

import lombok.Data;

@Data
public class QuickLogDTO {
	private Long taskId; 
	private String title; 
	private String statusDetails;
	private String name;
	private LocalDateTime time;
	private String logTime;
	
	public QuickLogDTO(Object[] data) {
		this.taskId = (long)(int)data[0]; 
		this.title = (String)data[1];
		this.statusDetails = (String)data[2];
		this.name = (String)data[3];
		this.time =  Timestamp.valueOf(data[4].toString()).toLocalDateTime();
		this.logTime = FunctionsUtils.formatTimeStamp(this.time);
	}
}
