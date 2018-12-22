package com.javaee.bruno.mercadoacoes.services;

import java.util.Set;

import com.javaee.bruno.mercadoacoes.domain.MessageId;
import com.javaee.bruno.mercadoacoes.domain.Acao;
import com.javaee.bruno.mercadoacoes.domain.AcaoDemanda;
import com.javaee.bruno.mercadoacoes.domain.AcaoMercado;
import com.javaee.bruno.mercadoacoes.domain.AcaoOferta;

public interface AcaoService {
	Set<Acao> getAll();

	Acao getById(String id);

	Acao createNew(Acao acao);

	Acao emitNew(String empresaId, Acao acao);

	Acao save(String id, Acao acao);
		
	void deleteById(String id);
	
	Set<AcaoDemanda> getAllDemandas();

	Set<AcaoOferta> getAllOfertas();

	MessageId sendMessage(String source, AcaoMercado acaoMercado);
}
