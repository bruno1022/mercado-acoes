package com.javaee.bruno.mercadoacoes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.bruno.mercadoacoes.domain.Investidor;

@Repository
public interface InvestidorRepository extends  MongoRepository<Investidor, String>{
	List<Investidor> findByNome(String nome);
	List<Investidor> findByEmail(String email);
}
