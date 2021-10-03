package com.sangam.sangam.beans;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@SessionScoped
@Data
public class ErrorBean {
	private String errorMessage;
	
	
}
