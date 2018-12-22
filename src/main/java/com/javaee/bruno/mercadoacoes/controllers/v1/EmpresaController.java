package com.javaee.bruno.mercadoacoes.controllers.v1;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaee.bruno.mercadoacoes.domain.Empresa;
import com.javaee.bruno.mercadoacoes.domain.EmpresaRest;
import com.javaee.bruno.mercadoacoes.services.EmpresaService;

@RestController
@RequestMapping(EmpresaController.BASE_URL)
public class EmpresaController {

	public static final String BASE_URL = "/api/v1/empresas";

	@Autowired
	private EmpresaService empresaService;

	// GET
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<EmpresaRest> getAll() {
		Set<EmpresaRest> empresaRest = new HashSet<>();
		empresaService.getAll().forEach((Empresa empresa) -> {
			empresaRest.add(new EmpresaRest(empresa));
		});
		return empresaRest;
	}

	@GetMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public EmpresaRest getById(@PathVariable String id) {
		return new EmpresaRest(empresaService.getById(id));
	}

	
	
	// POST
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EmpresaRest createNew(@RequestBody Empresa empresa) {
		return new EmpresaRest(empresaService.createNew(empresa));
	}

	

	// PUT
	@PutMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public EmpresaRest update(@PathVariable String id, @RequestBody Empresa empresa) {
		Empresa comp = empresaService.getById(id);
		comp.setNome(empresa.getNome());
		comp.setEmail(empresa.getEmail());
		return new EmpresaRest(empresaService.save(id, comp));
	}

	// DELETE
	@DeleteMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String id) {
		empresaService.deleteById(id);
	}
}
