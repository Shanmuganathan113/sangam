package com.sangam.sangam.beans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sangam.sangam.dto.QuestionDTO;
import com.sangam.sangam.utils.InternalQuestionService;

import lombok.Data;

@Component
@Scope("request")
@Data
public class QuestionAnswersBean {
	
	@Autowired
	InternalQuestionService questionService;
	
	private String section;
	private List<QuestionDTO> questions= new ArrayList<QuestionDTO>();
	private String questionAnsJSON;
	
	public void loadQuestionAnswers() {
		questions = questionService.fetchQuestionsAnswers(section);
	}
	
	public void uploadQuestionsAnswers() {
		
		System.out.println(" &&&&&& >>> "+questionAnsJSON);
		
		
		String[] arrOfStr = questionAnsJSON.split("==string_splitter__");
		System.out.println("Size >> "+arrOfStr.length);
		String questionAnswersJSON = arrOfStr[0];
		String paragraphsJSON = arrOfStr[1];
		
		System.out.println(" QA "+questionAnswersJSON);
		
		System.out.println(" paragraph "+paragraphsJSON);
		
		
		
		
	}
	
}
