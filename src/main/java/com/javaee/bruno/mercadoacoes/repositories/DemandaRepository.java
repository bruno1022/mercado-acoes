package com.javaee.bruno.mercadoacoes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.bruno.mercadoacoes.domain.Acao;
import com.javaee.bruno.mercadoacoes.domain.AcaoDemanda;

@Repository
public interface DemandaRepository extends MongoRepository<AcaoDemanda, String>{
	List<DemandaRepository> findByAcao(Acao acao);
}
