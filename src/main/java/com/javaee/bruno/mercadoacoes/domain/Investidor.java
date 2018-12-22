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
@Document(collection="investidores")
public class Investidor {

	@Id
	private String id = UUID.randomUUID().toString();
	private String nome;
	private String email;
	private String timestamp = new Timestamp(System.currentTimeMillis()).toString();

	@DBRef(lazy = true)
	private Set<AcaoInvestidor> acoes = new HashSet<>();

	@DBRef(lazy = true)
	private Set<AcaoDemanda> demandas = new HashSet<>();

	@DBRef(lazy = true)
	private Set<AcaoOferta> ofertas = new HashSet<>();

	@DBRef(lazy = true)
	private Set<AcaoMercado> mercado = new HashSet<>();
}
