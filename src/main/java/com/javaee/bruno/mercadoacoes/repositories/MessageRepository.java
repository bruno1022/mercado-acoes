package com.javaee.bruno.mercadoacoes.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.bruno.mercadoacoes.domain.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, String>{
}
