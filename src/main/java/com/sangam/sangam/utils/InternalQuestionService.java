package com.sangam.sangam.utils;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sangam.sangam.dto.QuestionDTO;

@FeignClient(name = "InternalQuestionService",url="http://localhost:8081/")
public interface InternalQuestionService {

	 @RequestMapping(value = "/questionsAnswers",  method = RequestMethod.GET, params = {"section"}, produces = MediaType.APPLICATION_JSON_VALUE)
	 List<QuestionDTO> fetchQuestionsAnswers(@RequestParam("section") String section);
}
