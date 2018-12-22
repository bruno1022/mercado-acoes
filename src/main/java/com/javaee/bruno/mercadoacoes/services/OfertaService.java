package com.javaee.bruno.mercadoacoes.services;

import com.javaee.bruno.mercadoacoes.domain.AcaoMercado;
import com.javaee.bruno.mercadoacoes.domain.AcaoOferta;

public interface OfertaService {

	AcaoOferta createNew(AcaoMercado stockMarket);
	
	AcaoOferta save(AcaoOferta offer);
	
}
