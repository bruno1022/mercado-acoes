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
@Document(collection="acoes")
public class Acao {
	@Id
	private String id = UUID.randomUUID().toString();

	private String nome;
	private int quantitidade;
	private int quantitidadeEmpresa;
	private float precoInicial;
	private String timestamp = new Timestamp(System.currentTimeMillis()).toString();
		
	@DBRef(lazy = true)
	private Empresa company;

	@DBRef(lazy = true)
	private Set<AcaoInvestidor> donos = new HashSet<>();

	@DBRef(lazy = true)
	private Set<AcaoDemanda> demandas = new HashSet<>();

	@DBRef(lazy = true)
	private Set<AcaoOferta> ofertas = new HashSet<>();

	@DBRef(lazy = true)
	private Set<AcaoMercado> mercado = new HashSet<>();
}