package com.sangam.sangam.dto;

import java.util.List;

import lombok.Data;

@Data
public class QuestionDTO {

	private Long questionId;
	
	private String question;
	
	private String descriptionForCorrectAnswer;
	
	private Integer sectionId;
	
	private Long userId;
	
	private List<QuestionAnswersDTO> answers;
	
	private QuestionParagraphDTO paragraph;
	
	private String paragraphInd ;
	
	private String time;
	
	
}
