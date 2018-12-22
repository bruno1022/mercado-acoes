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
@Document(collection="acao-demanda")
public class AcaoDemanda { // Consumer
	
	@Id
	private String id = UUID.randomUUID().toString();
	private int quantidade;
	private int quantidadeComprador;
	private float preco;
	private String timestamp = new Timestamp(System.currentTimeMillis()).toString();

	@DBRef(lazy = true)
	private Investidor investidor;
	@DBRef(lazy = true)
	private Acao acao;
	@DBRef(lazy = true)
	private Set<Mercado> mercado = new HashSet<>();;

}
