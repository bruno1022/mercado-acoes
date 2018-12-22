package com.javaee.bruno.mercadoacoes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.bruno.mercadoacoes.domain.Empresa;

@Repository
public interface EmpresaRepository extends MongoRepository<Empresa, String>{
	List<Empresa> findByNome(String nome);
	List<Empresa> findByEmail(String email);
}
