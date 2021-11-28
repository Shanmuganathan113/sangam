package com.sangam.sangam.beans;

import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sangam.sangam.dto.MessageDTO;
import com.sangam.sangam.service.MessageService;
import com.sangam.sangam.web.security.UserPrincipal;

import lombok.Data;

/*
 * This bean will deal with all messages
 * 
 * */

@Component
@ViewScoped
@Data
public class MessageBean {
	
	private String statusMessage;
	private String myUserId;
	
	@Autowired
	MessageService messageService;
	
	MessageDTO messageDTO = new MessageDTO();
	
	public void sendMessage() {
		this.statusMessage = messageService.sendMessage(messageDTO);
	}

	public void loadUserDetails() {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		
		if(loggedInUser != null && loggedInUser.isAuthenticated()) {
			UserPrincipal userDetails = (UserPrincipal)loggedInUser.getPrincipal();
			this.myUserId = userDetails.getId();
		}
		else {
		    this.myUserId = null;
		}
	}
	
}
