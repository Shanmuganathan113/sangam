package com.sangam.sangam.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sangam.sangam.beans.ErrorBean;

@Aspect
@Component
public class SangamAspect {
	
	@Autowired
	ErrorBean error;
	
	private static final Logger logger = LoggerFactory.getLogger(SangamAspect.class);

	@AfterThrowing(pointcut = "execution(* com.sangam.sangam.*.*.*(..)) && args(methodParams,..)",throwing = "ex")
	public void logException(JoinPoint jp, Object methodParams, Exception ex) {
		ObjectMapper obj = new ObjectMapper();
		try {
			logger.error("---------------------------- " 
			+"\n Method      >>"		+	jp.toString()
			+"\n line number >>"		+	ex.getStackTrace()[0].getLineNumber()
			+"\n Arguments   >>"		+	obj.writeValueAsString(methodParams) 
			+"\n Exception   >>"		+	ex.getStackTrace()[0]
			+"\n Exception   >>"		+	ex.getMessage());
			error.setErrorMessage(ex.getMessage());
		}	
		catch(Exception e) {
		}
	}
	
	@AfterThrowing(pointcut = "execution(* com.sangam.sangam.*.*.*(..)) ",throwing = "ex")
	public void logExceptionForMethodWithoutParams(JoinPoint jp, Exception ex) {
		try {
			logger.error("---------------------------- " 
			+"\n Method      >>"		+	jp.toString()
			+"\n line number >>"		+	ex.getStackTrace()[0].getLineNumber()
			+"\n Exception   >>"		+	ex.getStackTrace()[0]);
		}	
		catch(Exception e) {
		}
	}
}
