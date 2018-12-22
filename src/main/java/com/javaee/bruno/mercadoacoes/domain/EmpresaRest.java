package com.javaee.bruno.mercadoacoes.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaRest {
	public EmpresaRest(Empresa empresa) {
		this.setId(empresa.getId());
		this.setNome(empresa.getNome());
		this.setEmail(empresa.getEmail());
		this.setTimestamp(empresa.getTimestamp());
	}

	private String id;
	private String nome;
	private String email;
	private String timestamp;

}
