package com.javaee.bruno.mercadoacoes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.bruno.mercadoacoes.domain.Message;
import com.javaee.bruno.mercadoacoes.repositories.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	MessageRepository messageRepository;

	@Override
	public Message createNew(Message message) {
		return messageRepository.save(message);
	}

}
