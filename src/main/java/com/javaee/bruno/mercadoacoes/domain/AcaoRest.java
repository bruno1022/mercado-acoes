package com.javaee.bruno.mercadoacoes.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcaoRest {
	public AcaoRest(Acao acao) {
		this.setId(acao.getId());
		this.setNome(acao.getNome());
		this.setQuantitidade(acao.getQuantitidade());
		this.setQuantitidadeEmpresa(acao.getQuantitidadeEmpresa());
		this.setPrecoInicial(acao.getPrecoInicial());
		this.setTimestamp(acao.getTimestamp());
	}

	private String id;
	private String nome;
	private int quantitidade;
	private int quantitidadeEmpresa;
	private float precoInicial;
	private String timestamp;

}