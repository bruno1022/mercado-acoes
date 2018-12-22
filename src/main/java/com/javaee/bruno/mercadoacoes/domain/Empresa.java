package com.javaee.bruno.mercadoacoes.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="empresas")
public class Empresa {

	@Id
	private String id = UUID.randomUUID().toString();
	private String nome;
	private String email;
	private String timestamp = new Timestamp(System.currentTimeMillis()).toString();
		
	@DBRef(lazy = true)
	private Set<Acao> acoes = new HashSet<>();

}
