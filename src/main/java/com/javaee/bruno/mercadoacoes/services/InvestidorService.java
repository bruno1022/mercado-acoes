package com.javaee.bruno.mercadoacoes.services;

import java.util.Set;

import com.javaee.bruno.mercadoacoes.domain.Investidor;

public interface InvestidorService {
	Set<Investidor> getAll();

	Investidor getById(String id);

	Investidor createNew(Investidor buyer);

	Investidor save(String id, Investidor buyer);

	void deleteById(String id);
}
