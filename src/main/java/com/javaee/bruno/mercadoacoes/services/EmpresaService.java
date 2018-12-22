package com.javaee.bruno.mercadoacoes.services;

import java.util.Set;

import com.javaee.bruno.mercadoacoes.domain.Empresa;
import com.javaee.bruno.mercadoacoes.domain.Acao;

public interface EmpresaService {
	Set<Empresa> getAll();

	Empresa getById(String id);

	Empresa createNew(Empresa company);

	Empresa save(String id, Empresa company);

	void deleteById(String id);

	Empresa addStock(String id, Acao stock);

	Set<Acao> getAllStocks(String companyId);

	Acao getStockById(String companyId, String stockId);
}
