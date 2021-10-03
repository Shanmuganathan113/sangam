package com.sangam.sangam.service.impl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sangam.sangam.domain.Message;
import com.sangam.sangam.dto.MessageDTO;
import com.sangam.sangam.repository.MessageRepository;
import com.sangam.sangam.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	MessageRepository messageRepository;
	
	@Override
	public String sendMessage(MessageDTO messageDTO) {
		System.out.println("Message DTO "+messageDTO);
		ModelMapper modelMapper = new ModelMapper();
		Message message = modelMapper.map(messageDTO,Message.class);
		System.out.println("Message Entity "+message);
		
		message.setTime(LocalDateTime.now());
		messageRepository.save(message);
		return "Message has been sent successfully";
	}

}
