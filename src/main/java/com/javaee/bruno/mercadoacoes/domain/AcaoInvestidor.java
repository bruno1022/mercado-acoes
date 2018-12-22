package com.javaee.bruno.mercadoacoes.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="acao-investidor")
public class AcaoInvestidor {
	
	@Id
	private String id = UUID.randomUUID().toString();
	
	private int quantidade;
	private String AcaoId;
	private String InvestidorId;
	
}
