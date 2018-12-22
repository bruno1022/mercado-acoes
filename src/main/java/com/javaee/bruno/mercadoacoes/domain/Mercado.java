package com.javaee.bruno.mercadoacoes.domain;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="mercado")
public class Mercado {

	@Id
	private String id = UUID.randomUUID().toString();
	private int quantidade;
	private float preco;
	private String timestamp = new Timestamp(System.currentTimeMillis()).toString();
		
	@DBRef(lazy = true)
	private AcaoDemanda demanda;
	@DBRef(lazy = true)
	private AcaoOferta oferta;

}