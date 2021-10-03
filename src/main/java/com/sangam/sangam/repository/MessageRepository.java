package com.sangam.sangam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangam.sangam.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
}
