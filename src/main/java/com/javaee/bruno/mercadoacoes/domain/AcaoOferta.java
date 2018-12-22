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
@Document(collection="acao-oferta")
public class AcaoOferta { // vendedores
	
	@Id
	private String id = UUID.randomUUID().toString();
	private boolean empresaOferta = false;
	private int quantidade;
	private int quantidadeVendido;
	private float preco;
	private String timestamp = new Timestamp(System.currentTimeMillis()).toString();
	
	@DBRef(lazy = true)
	private Investidor investidor;
	@DBRef(lazy = true)
	private Acao acao;
	@DBRef(lazy = true)
	private Set<Mercado> mercado = new HashSet<>();

}
