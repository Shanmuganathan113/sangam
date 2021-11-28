package com.sangam.sangam.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface Constants {
	public Integer ROLE_TEAM_LEAD = 1;
	public Integer ROLE_TEAM_MEMBER = 2;
	public Integer ROLE_GLOBAL_ADMIN = 3;
	
	public Integer TASK_CREATE_INDICATOR = 101;
	
	public Integer SEGMENT_TASK = 1;
	
	public static final String UTILS_CREATE_TASK 	= "createTask";
	public static final String UTILS_UPDATE_TASK 	= "updateTask";
	public static final String UTILS_FILTER_TASK 	= "filterTask";
	public static final String UTILS_LOAD_STATICS 	= "loadStatics";
	
	public static final Integer IND_TASK_CREATED 	= 101;
	public static final Integer IND_TASK_QUEUED 	= 151;
	
	public static final String	URL_PARAM_TASK_DETAIL 	= "taskDetail";
	public static final String	URL_PARAM_TASK_UTILS 	= "taskUtils";
	public static final String	URL_PARAM_TEAM_TASKS 	= "teamTasks";
	public static final String	URL_PARAM_QUEUE_TASKS 	= "queueTasks";
	public static final String	URL_PARAM_CREATE_TASK 	= "createTask";
	public static final String	URL_PARAM_FILTER_TASK 	= "filterTask";
	public static final String	URL_PARAM_MY_TASK		= "myTasks";
	
	public static final Integer IND_LOAD_TEAM_MEMBERS 	= 102;
	public static final Integer IND_LOAD_TEAM_LIST 		= 151;
	
	public static final String STATIC_TASK_PARAM_MY_TASK 		= "1";
	public static final String STATIC_TASK_PARAM_MY_TEAM_TASK 	= "2";
	public static final String STATIC_TASK_PARAM_MY_QUEUE_TASK	= "3";
	public static final String STATIC_TASK_PARAM_SPECIFIC_TASK	= "4";
	
	public static final String STATIC_TASK_PARAM_FLAG = "flag";
	public static final String STATIC_TASK_PARAM_USER_ID = "userId";
	public static final String STATIC_TASK_PARAM_ID = "id";
	
	public static final String STATIC_READ_STATUS_IND_TASK = "task";
	
	public static final String PAGE_TITLE_TASK_UTILS 	= "Task Utils";
	public static final String PAGE_TITLE_TEAM_TASK 	= "Tasks";
	public static final String PAGE_TITLE_QUEUE 		= "Queue";
	public static final String PAGE_TITLE_CREATE_TASK 	= "Create Task";
	public static final String PAGE_TITLE_FILTER_TASK 	= "Filter Task";
	public static final String PAGE_TITLE_MY_TASK 		= "My Tasks";
	
	public static String getString(Object obj) {
		if(obj!= null)
			return obj.toString();
		return null;
	}
	
	public static Long getLong(Object obj) {
		if(obj!= null)
			return Long.parseLong(obj.toString());
		return null;
	}
	
	public static Integer getInteger(Object obj) {
		if(obj!= null)
			return Integer.parseInt(obj.toString());
		return null;
	}
	
	public static LocalDateTime getLocalDateTime(Object data) {
		if (data != null)
			return Timestamp.valueOf(data.toString()).toLocalDateTime();
		else 
			return null;
	}
	
	public static String getStringTime(LocalDateTime time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return time.format(formatter);
	}
}
