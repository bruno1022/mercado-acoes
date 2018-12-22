package com.javaee.bruno.mercadoacoes.controllers.v1;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaee.bruno.mercadoacoes.domain.MessageId;
import com.javaee.bruno.mercadoacoes.domain.Acao;
import com.javaee.bruno.mercadoacoes.domain.AcaoDemanda;
import com.javaee.bruno.mercadoacoes.domain.AcaoDemandaRest;
import com.javaee.bruno.mercadoacoes.domain.AcaoMercado;
import com.javaee.bruno.mercadoacoes.domain.AcaoOferta;
import com.javaee.bruno.mercadoacoes.domain.AcaoOfertaRest;
import com.javaee.bruno.mercadoacoes.domain.AcaoRest;
import com.javaee.bruno.mercadoacoes.services.EmpresaService;
import com.javaee.bruno.mercadoacoes.services.AcaoService;

@RestController
@RequestMapping(AcaoController.BASE_URL)
public class AcaoController {

	public static final String BASE_URL = "/api/v1/acoes";

	@Autowired
	private AcaoService acaoService;
	@Autowired
	private EmpresaService empresaService;

	/* Listar ações - I */
	// GET
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<AcaoRest> getAll() {
		Set<AcaoRest> stockRest = new HashSet<>();
		acaoService.getAll().forEach((Acao stock) -> {
			stockRest.add(new AcaoRest(stock));
		});
		return stockRest;
	}

	@GetMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public AcaoRest getById(@PathVariable String id) {
		return new AcaoRest(acaoService.getById(id));
	}
	/* Listar ações - F */

	/* Emissão de Ações - I */	
	// GET
	@GetMapping({ "/emit/{companyId}" })
	@ResponseStatus(HttpStatus.OK)
	public Set<AcaoRest> getAllCompany(@PathVariable String companyId) {
		Set<AcaoRest> stockRest = new HashSet<>();
		empresaService.getAllStocks(companyId).forEach((Acao stock) -> {
			stockRest.add(new AcaoRest(stock));
		});
		return stockRest;
	}

	@GetMapping({ "/emit/{companyId}/{acaoId}" })
	@ResponseStatus(HttpStatus.OK)
	public AcaoRest getById(@PathVariable String companyId, @PathVariable String stockId) {
		return new AcaoRest (empresaService.getStockById(companyId, stockId));
	}

	// POST
	@PostMapping({ "/emit/{empresaId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public AcaoRest createNew(@PathVariable String empresaId, @RequestBody Acao acao) {
		return new AcaoRest(acaoService.emitNew(empresaId, acao));
	}
	/* Emissão de Ações - F */

	/* Comprar ações - I */
	// GET
	@GetMapping({ "/comprar" })
	@ResponseStatus(HttpStatus.OK)
	public Set<AcaoDemandaRest> getDemands() {
		Set<AcaoDemandaRest> demandsRest = new HashSet<>();
		acaoService.getAll().forEach((Acao acao) -> {
			acao.getDemandas().forEach((AcaoDemanda demanda) -> {
				if (demanda.getQuantidade() - demanda.getQuantidadeComprador() > 0) {
					demandsRest.add(new AcaoDemandaRest(demanda));
				}
			});
		});
		return demandsRest;
	}

	// POST
	@PostMapping({ "/comprar" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId buy(@RequestBody AcaoMercado acaoMercado) {
		return acaoService.sendMessage("demanda", acaoMercado);
	}

	@PostMapping({ "/buy/{acaoId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId buyStock(@PathVariable String acaoId, @RequestBody AcaoMercado acaoMercado) {
		acaoMercado.setAcaoId(acaoId);
		return acaoService.sendMessage("demanda", acaoMercado);
	}

	@PostMapping({ "/buy/{acaoId}/{investidorId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId buyStockInvestor(@PathVariable String acaoId, @PathVariable String investidorId, @RequestBody AcaoMercado acaoMercado) {
		acaoMercado.setAcaoId(acaoId);
		acaoMercado.setInvestidorId(investidorId);
		return acaoService.sendMessage("demanda", acaoMercado);
	}
	/* Comprar ações - F */

	/* Vender ações - I */
	// GET
	@GetMapping({ "/vender" })
	@ResponseStatus(HttpStatus.OK)
	public Set<AcaoOfertaRest> getOffers() {
		Set<AcaoOfertaRest> acaoOfertaRest = new HashSet<>();
		acaoService.getAll().forEach((Acao acao) -> {
			acao.getOfertas().forEach((AcaoOferta oferta) -> {
				if (oferta.getQuantidade() - oferta.getQuantidadeVendido() > 0) {
					acaoOfertaRest.add(new AcaoOfertaRest(oferta));
				}
			});
		});
		return acaoOfertaRest;
	}

	// POST
	@PostMapping({ "/vender" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId sell(@RequestBody AcaoMercado acaoMercado) {
		return acaoService.sendMessage("oferta", acaoMercado);
	}

	@PostMapping({ "/vender/{acaoId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId sellStock(@PathVariable String acaoId, @RequestBody AcaoMercado acaoMercado) {
		acaoMercado.setAcaoId(acaoId);
		return acaoService.sendMessage("oferta", acaoMercado);
	}

	@PostMapping({ "/sell/{acaoId}/{investidorId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId sellStockInvestor(@PathVariable String acaoId, @PathVariable String investidorId, @RequestBody AcaoMercado acaoMercado) {
		acaoMercado.setAcaoId(acaoId);
		acaoMercado.setInvestidorId(investidorId);
		return acaoService.sendMessage("oferta", acaoMercado);
	}
	/* Vender ações - F */

	
}
