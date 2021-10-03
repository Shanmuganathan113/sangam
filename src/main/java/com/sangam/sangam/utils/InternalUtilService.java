package com.sangam.sangam.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sangam.sangam.dto.UtilXLDataDTO;

@FeignClient(name = "InternalUtilService",url="${internalService.utilServiceURL}")
public interface InternalUtilService {

	@RequestMapping(value = "/utilManageTask", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String manageTasks(@RequestBody UtilXLDataDTO utilTasks);
	
	@RequestMapping(value = "/utilFilterTask", method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String fetchTaskReport(@RequestBody UtilXLDataDTO utilXLDataDTO);
	
	@RequestMapping(value = "/utilRefreshStatics",method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String fetchStatics(@RequestBody UtilXLDataDTO utilXLDataDTO);

}
