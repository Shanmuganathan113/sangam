package com.sangam.sangam.utils;

import java.util.Map;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sangam.sangam.dto.TeamDTO;

@FeignClient(name = "InternalTeamService",url="${internalService.teamServiceURL}" )
public interface InternalTeamService {

	@RequestMapping
	 Set<TeamDTO> fetchTeamDetails(@RequestParam Map<String,String> mapParams);

}
