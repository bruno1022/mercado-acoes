package com.javaee.bruno.mercadoacoes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.bruno.mercadoacoes.domain.Mercado;
import com.javaee.bruno.mercadoacoes.repositories.MercadoRepository;

@Service
public class MercadoServiceImpl implements MercadoService {
	
	@Autowired
	MercadoRepository marketRepository;

	@Override
	public Mercado save(Mercado market) {
		market.setId(market.getId());
		return marketRepository.save(market);
	}

}
