package com.javaee.bruno.mercadoacoes.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcaoDemandaRest { // Consumer
	
	public AcaoDemandaRest(AcaoDemanda acaoDemanda) {
		this.setId(acaoDemanda.getId());
		this.setQuantidade(acaoDemanda.getQuantidade());
		this.setQuantidadeComprador(acaoDemanda.getQuantidadeComprador());
		this.setPreco(acaoDemanda.getPreco());
		this.setTimestamp(acaoDemanda.getTimestamp());
		this.setInvestidor(new InvestidorRest(acaoDemanda.getInvestidor()));
		this.setAcao(new AcaoRest(acaoDemanda.getAcao()));
	}
	public AcaoDemandaRest() {
	}
	
	private String id;
	private int quantidade;
	private int quantidadeComprador;
	private float preco;
	private String timestamp;

	private InvestidorRest investidor;
	private AcaoRest acao;

}
