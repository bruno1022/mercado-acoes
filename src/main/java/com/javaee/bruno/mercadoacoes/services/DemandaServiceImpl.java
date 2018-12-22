package com.javaee.bruno.mercadoacoes.services;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.bruno.mercadoacoes.comparators.AcaoDemandaComparatorByAcaoTimestamp;
import com.javaee.bruno.mercadoacoes.domain.Investidor;
import com.javaee.bruno.mercadoacoes.domain.Mercado;
import com.javaee.bruno.mercadoacoes.domain.Acao;
import com.javaee.bruno.mercadoacoes.domain.AcaoDemanda;
import com.javaee.bruno.mercadoacoes.domain.AcaoMercado;
import com.javaee.bruno.mercadoacoes.domain.AcaoOferta;
import com.javaee.bruno.mercadoacoes.emailsender.EmailSender;
import com.javaee.bruno.mercadoacoes.repositories.DemandaRepository;

@Service
public class DemandaServiceImpl implements DemandaService {
	
	@Autowired
	DemandaRepository demandaRepository;
	@Autowired
	InvestidorService investidorService;
	@Autowired
	OfertaService ofertaService;
	@Autowired
	AcaoService acaoService;
	@Autowired
	MercadoService mercadoService;

	@Override
	public AcaoDemanda createNew(AcaoMercado acaoMercado) {
		EmailSender emailSender = new EmailSender();
		Investidor investor = investidorService.getById(acaoMercado.getInvestidorId());
		Acao acao = acaoService.getById(acaoMercado.getAcaoId());

		AcaoDemanda acaoDemanda = new AcaoDemanda();
		acaoDemanda.setQuantidade(acaoMercado.getQuantidade());
		acaoDemanda.setQuantidadeComprador(0);
		acaoDemanda.setPreco(acaoMercado.getPreco());
		acaoDemanda.setInvestidor(investor);
		acaoDemanda.setAcao(acao);

		// Verifica se possui alguma oferta no mesmo valor
		Set<AcaoOferta> acaoOferta = acao.getOfertas();
		acaoOferta.stream().forEach((acaoOferta) -> {
			int quant = 0;
			if (acaoOferta.getPreco() == acaoDemanda.getPreco() && acaoOferta.getQuantidade() - acaoOferta.getQuantidadeVendido() > 0) {
				if (acaoOferta.getQuantidade() - acaoOferta.getQuantidadeVendido() > acaoDemanda.getQuantidade() - acaoDemanda.getQuantidadeComprador()) {
					quant = acaoDemanda.getQuantidade() - acaoDemanda.getQuantidadeComprador();
				} else {
					quant = acaoOferta.getQuantidade() - acaoOferta.getQuantidadeVendido();
				}

				// Remove quantidade em ambos as ofertas
				if (quant > 0) {
					acaoDemanda.setQuantidade(acaoDemanda.getQuantidadeComprador() + quant);
					acaoOferta.setQuantidade(acaoOferta.getQuantidadeVendido() + quant);
					ofertaService.save(acaoOferta);
					
					Mercado mercado = new Mercado();
					mercado.setQuantidade(quant);
					mercado.setPreco(acaoDemanda.getPreco());
					mercado.setOferta(acaoOferta);
					mercado.setDemanda(acaoDemanda);
					mercadoService.save(mercado);
					
					if (acaoOferta.isEmpresaOferta()) {
						acao.setQuantitidadeEmpresa(acao.getQuantitidadeEmpresa() - quant);
					}
					
					if (acaoOferta.isEmpresaOferta()) {
						emailSender.SendEmail(acaoOferta.getAcao().getCompany().getEmail(), 
								"Notificação de venda ação " + acaoOferta.getAcao().getId(), 
								Integer.toString(quant) + " ações foram vendidas com sucesso no valor de " + acaoDemanda.getPreco() + " (preço unitário)."
						);
					} else {
						emailSender.SendEmail(acaoOferta.getInvestidor()).getEmail(), 
								"Notificação de venda ação " + acaoOferta.getAcao().getId(), 
								Integer.toString(quant) + " ações foram vendidas com sucesso no valor de " + acaoDemanda.getPreco() + " (preço unitário)."
						);
					}
					emailSender.SendEmail(acaoDemanda.getInvestidor().getEmail(), 
							"Notificação de compra ação " + acaoOferta.getAcao().getId(), 
							Integer.toString(quant) + " ações foram compradas com sucesso no valor de " + acaoDemanda.getPreco() + " (preço unitário)."
					);

				}
			}
		});
		demandaRepository.save(acaoDemanda);

		Set<AcaoDemanda> sDemands = acao.getDemandas();
		sDemands.add(acaoDemanda);
		acao.setDemandas(sDemands);
		acaoService.save(acao.getId(), acao);

		Set<AcaoDemanda> iDemands = investor.getDemandas();
		iDemands.add(acaoDemanda);
		investor.setDemandas(iDemands);
		investidorService.save(investor.getId(), investor);
		
		return acaoDemanda;
	}

	@Override
	public Set<AcaoDemanda> getAll() {
		Set<AcaoDemanda> stockDemands = new TreeSet<AcaoDemanda>(new AcaoDemandaComparatorByAcaoTimestamp());
		demandaRepository.findAll().iterator().forEachRemaining(stockDemands::add);

		// Remove itens consumidos
		Predicate<AcaoDemanda> demandPredicate = d-> ( d.getQuantidade() - d.getQuantidadeComprador() ) == 0;
		stockDemands.removeIf(demandPredicate);

		// Ordena saída
		//Set<StockDemand> sorted = new TreeSet<StockDemand>(new StockDemandComparatorByIdAndTimestamp());
		//sorted.addAll(stockDemands);

		return stockDemands;
	}

	@Override
	public Set<AcaoDemanda> getAllByAcao(Acao acao) {
		Set<AcaoDemanda> stockDemands = this.getAll();

		// Remove itens de outros Stocks
		Predicate<AcaoDemanda> demandPredicate = d-> d.getAcao().getId() != acao.getId();
		stockDemands.removeIf(demandPredicate);

		return stockDemands;
	}

	@Override
	public AcaoDemanda save(AcaoDemanda demand) {
		demand.setId(demand.getId());
		return demandaRepository.save(demand);
	}

}
