package com.javaee.bruno.mercadoacoes.services;

import java.util.Set;

import com.javaee.bruno.mercadoacoes.domain.Acao;
import com.javaee.bruno.mercadoacoes.domain.AcaoDemanda;
import com.javaee.bruno.mercadoacoes.domain.AcaoMercado;

public interface DemandaService {

	AcaoDemanda createNew(AcaoMercado acaoMercado);
	
	Set<AcaoDemanda> getAll();

	Set<AcaoDemanda> getAllByAcao(Acao acao);

	AcaoDemanda save(AcaoDemanda demanda);

}
