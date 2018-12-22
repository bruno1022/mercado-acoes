package com.javaee.bruno.mercadoacoes.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcaoMercado {
	
	
	private boolean empresaOferta = false;
	private String investidorId;
	private String acaoId;
	private int quantidade;
	private float preco;
	
}
