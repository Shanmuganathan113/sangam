package com.sangam.sangam.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name = "InternalSlideShowQuizService",url="http://localhost:8081/")
public interface InternalSlideShowQuizService {
	
	@RequestMapping(value = "/slideShow/quiz", method = RequestMethod.GET, params = {"id"}, produces = MediaType.APPLICATION_JSON_VALUE )
	public String fetchSlideShowQuiz(@RequestParam("id") String slideShowQuizId);
}
