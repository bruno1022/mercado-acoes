package com.javaee.bruno.mercadoacoes.domain;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="messages")
public class Message implements Serializable{
	public Message(String source, AcaoMercado acaoMercado) {
		this.setSource(source);
		this.setData(acaoMercado);
	}
	public Message() {		
	}
	
	private static final long serialVersionUID = 1L;
	
	private String id = UUID.randomUUID().toString();
	private String source; 
	private AcaoMercado data;
}