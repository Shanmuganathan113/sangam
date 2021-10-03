package com.sangam.sangam.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "table_message")
@Data
public class Message {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="t_m_id")
	    private Long id;
	 	
	 	private String subject;
	 	
	 	private String content;
	 	
	 	private Long sentBy;
	 	
	 	private Long sentTo;
	 	
	 	private String flag;
	 	
	 	private LocalDateTime time;
}
