package com.javaee.bruno.mercadoacoes.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.bruno.mercadoacoes.domain.Investidor;
import com.javaee.bruno.mercadoacoes.repositories.InvestidorRepository;

@Service
public class InvestidorServiceImpl implements InvestidorService {

	@Autowired
	private InvestidorRepository investidorRepository;

	@Override
	public Set<Investidor> getAll() {
		Set<Investidor> investors = new HashSet<>();
		investidorRepository.findAll().iterator().forEachRemaining(investors::add);
		return investors;
	}

	@Override
	public Investidor getById(String id) {
		return getInvestorById(id);
	}

	private Investidor getInvestorById(String id) {
		Optional<Investidor> investorOptional = investidorRepository.findById(id);

		if (!investorOptional.isPresent()) {
			throw new IllegalArgumentException("Investidor não localizado para o ID de valor: " + id.toString());
		}

		return investorOptional.get();
	}
	
	@Override
	public Investidor createNew(Investidor investor) {
		Investidor investorInd0;
		try {
			investorInd0 = investidorRepository.findByNome(investor.getNome()).get(0);
		} catch (Exception e) {
			return investidorRepository.save(investor);
		}
		throw new IllegalArgumentException("Já existe investudor com esse ID: " + investorInd0.getId());
	}

	@Override
	public Investidor save(String id, Investidor investor) {
		investor.setId(id);
		return investidorRepository.save(investor);
	}

	@Override
	public void deleteById(String id) {
		investidorRepository.deleteById(id);
	}

}
