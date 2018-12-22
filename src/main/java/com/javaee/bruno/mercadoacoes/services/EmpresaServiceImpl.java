package com.javaee.bruno.mercadoacoes.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.bruno.mercadoacoes.domain.Empresa;
import com.javaee.bruno.mercadoacoes.domain.Acao;
import com.javaee.bruno.mercadoacoes.repositories.EmpresaRepository;

@Service
public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	private EmpresaRepository companyRepository;
	@Autowired
	private AcaoService stockService;
	//@Autowired
	//private StockRepository stockRepository;

	@Override
	public Set<Empresa> getAll() {
		Set<Empresa> companies = new HashSet<>();
		companyRepository.findAll().iterator().forEachRemaining(companies::add);
		return companies;
	}

	@Override
	public Empresa getById(String id) {
		return getCompanyById(id);
	}

	private Empresa getCompanyById(String id) {
		Optional<Empresa> companyOptional = companyRepository.findById(id);

		if (!companyOptional.isPresent()) {
			throw new IllegalArgumentException("Não existe empresa para o ID de valor: " + id.toString());
		}

		return companyOptional.get();
	}

	@Override
	public Empresa createNew(Empresa company) {
		Empresa companyInd0;
		try {
			companyInd0 = companyRepository.findByEmail(company.getEmail()).get(0);
		} catch (Exception e) {
			return companyRepository.save(company);			
		}
		throw new IllegalArgumentException("Já existe empresa com o ID: " + companyInd0.getId());
	}

	@Override
	public Empresa save(String id, Empresa company) {
		company.setId(id);
		return companyRepository.save(company);
	}

	@Override
	public void deleteById(String id) {
		companyRepository.deleteById(id);
	}

	@Override
	public Empresa addStock(String companyId, Acao stock) {
		Empresa company = getCompanyById(companyId);
		Set<Acao> stocks = company.getAcoes();
		stocks.remove(null);
		stocks.add(stockService.createNew(stock));
		company.setAcoes(stocks);
		return save(companyId, company);
	}

	@Override
	public Set<Acao> getAllStocks(String companyId) {
		return getCompanyById(companyId).getAcoes();
	}

	@Override
	public Acao getStockById(String companyId, String stockId) {
		for (Acao stock : getCompanyById(companyId).getAcoes()) {
			if (stock.getId().equals(stockId)) {
				return stock;
			}
		}
		throw new IllegalArgumentException("Stock not found for ID value: " + stockId.toString() + " for Company " + companyId.toString());
	}

}
