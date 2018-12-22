package com.javaee.bruno.mercadoacoes.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcaoOfertaRest { // Comprador
	
	public AcaoOfertaRest(AcaoOferta acaoOferta) {
		this.setId(acaoOferta.getId());
		this.setQuantidade(acaoOferta.getQuantidade());
		this.setQuantidadeVendidos(acaoOferta.getQuantidadeVendido());
		this.setPreco(acaoOferta.getPreco());
		this.setTimestamp(acaoOferta.getTimestamp());
		if (acaoOferta.isEmpresaOferta()) {
			this.setEmpresaVendedor(true);
		} else {
			this.setEmpresaVendedor(false);
			this.setInvestidor(new InvestidorRest(acaoOferta.getInvestidor()));
		}
		this.setAcao(new AcaoRest(acaoOferta.getAcao()));
	}
	public AcaoOfertaRest() {
	}
	
	private String id;
	private boolean empresaVendedor;
	private int quantidade;
	private int quantidadeVendidos;
	private float preco;
	private String timestamp;

	private InvestidorRest investidor;
	private AcaoRest acao;

}
