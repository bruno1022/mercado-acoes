package com.javaee.bruno.mercadoacoes.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestidorRest {
	public InvestidorRest(Investidor investor) {
		this.setId(investor.getId());
		this.setNome(investor.getNome());
		this.setEmail(investor.getEmail());
		this.setTimestamp(investor.getTimestamp());
	}
	
	private String id;
	private String nome;
	private String email;
	private String timestamp;

}
