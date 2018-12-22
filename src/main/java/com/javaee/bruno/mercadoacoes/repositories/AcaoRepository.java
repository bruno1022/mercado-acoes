package com.javaee.bruno.mercadoacoes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.bruno.mercadoacoes.domain.Acao;

@Repository
public interface AcaoRepository extends MongoRepository<Acao, String>{
	List<Acao> findByNome(String nome);
}
