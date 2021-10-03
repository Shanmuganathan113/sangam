package com.sangam.sangam.utils;

import java.util.Map;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sangam.sangam.dto.FilterTaskDTO;
import com.sangam.sangam.dto.LogDTO;
import com.sangam.sangam.dto.MessageDTO;
import com.sangam.sangam.dto.TaskDTO;

@FeignClient(name = "InternalTaskService",url="${internalService.taskServiceURL}" )
public interface InternalTaskService {

	 @RequestMapping(value = "/taskDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	 Set<TaskDTO> fetchTaskDetails(@RequestParam Map<String,String> mapParams);
	 
	 @RequestMapping(value = "/updateTask",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	 TaskDTO updateTask(@RequestBody TaskDTO taskDTO);

	 @RequestMapping(value = "/updateTaskLog",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	 LogDTO updateTaskLog(@RequestBody LogDTO logDTO);
	 
	 @RequestMapping(value = "/messageDetails",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	 Set<MessageDTO> fetchMessageDetails(@RequestParam Map<String,String> listParams);
	 
	 @RequestMapping(value = "/updateMessage",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	 String updateMessage(@RequestBody MessageDTO messageDTO);
	 
	 @RequestMapping(value = "/updateMessageLog",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	 String updateMessageLog(@RequestBody MessageDTO messageDTO);
	 
	 @RequestMapping(value = "/filterTask",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	 Set<TaskDTO> fetchFilterTask(@RequestBody FilterTaskDTO filterTask);

}
