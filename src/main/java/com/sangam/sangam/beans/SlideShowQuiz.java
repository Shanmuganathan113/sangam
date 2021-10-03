package com.sangam.sangam.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sangam.sangam.utils.InternalSlideShowQuizService;

import lombok.Data;

@Component
@Scope("request")
@Data
public class SlideShowQuiz {
	@Autowired
	InternalSlideShowQuizService slideShowQuizService;
	
	String slideShowQuiz = null;
	String slideShowQuizId;
	
	
	public void loadQuiz() {
		System.out.println(" *** "+slideShowQuizId);
		slideShowQuiz = slideShowQuizService.fetchSlideShowQuiz(slideShowQuizId);
	}
	
	
}
