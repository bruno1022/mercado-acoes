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

import com.javaee.bruno.mercadoacoes.domain.Investidor;
import com.javaee.bruno.mercadoacoes.domain.InvestidorRest;
import com.javaee.bruno.mercadoacoes.services.InvestidorService;

@RestController
@RequestMapping(InvestidorController.BASE_URL)
public class InvestidorController {

	public static final String BASE_URL = "/api/v1/investidores";

	@Autowired
	private InvestidorService investidorService;

	// GET
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<InvestidorRest> getAll() {
		Set<InvestidorRest> investorsRest = new HashSet<>();
		investidorService.getAll().forEach((Investidor investidor) -> {
			investorsRest.add(new InvestidorRest(investidor));
		});
		return investorsRest;
	}

	@GetMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public InvestidorRest getById(@PathVariable String id) {
		return new InvestidorRest(investidorService.getById(id));
	}

	// POST
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InvestidorRest createNew(@RequestBody Investidor comprador) {
		return new InvestidorRest(investidorService.createNew(comprador));
	}

	// PUT
	@PutMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public InvestidorRest update(@PathVariable String id, @RequestBody Investidor comprador) {
		return new InvestidorRest(investidorService.save(id, comprador));
	}

	// DELETE
	@DeleteMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String id) {
		investidorService.deleteById(id);
	}
}
