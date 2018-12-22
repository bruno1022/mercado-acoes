package com.javaee.bruno.mercadoacoes.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.bruno.mercadoacoes.domain.Investidor;
import com.javaee.bruno.mercadoacoes.domain.Mercado;
import com.javaee.bruno.mercadoacoes.domain.Acao;
import com.javaee.bruno.mercadoacoes.domain.AcaoDemanda;
import com.javaee.bruno.mercadoacoes.domain.AcaoMercado;
import com.javaee.bruno.mercadoacoes.domain.AcaoOferta;
import com.javaee.bruno.mercadoacoes.emailsender.EmailSender;
import com.javaee.bruno.mercadoacoes.repositories.OfertaRepository;

@Service
public class OfertaServiceImpl implements OfertaService {
	
	@Autowired
	OfertaRepository offerRepository;
	@Autowired
	InvestidorService investorService;
	@Autowired
	DemandaService demandService;
	@Autowired
	AcaoService stockService;
	@Autowired
	MercadoService marketService;

	@Override
	public AcaoOferta createNew(AcaoMercado stockMarket) {
		EmailSender emailSender = new EmailSender();
		Investidor investor = null;
		if (!stockMarket.isEmpresaOferta()) {
			investor = investorService.getById(stockMarket.getInvestidorId());
		}
		Acao stock = stockService.getById(stockMarket.getAcaoId());

		AcaoOferta stockOffer = new AcaoOferta();
		stockOffer.setQuantidade(stockMarket.getQuantidade());
		stockOffer.setQuantidade(0);
		stockOffer.setPreco(stockMarket.getPreco());
		if (stockMarket.isEmpresaOferta()) {
			stockOffer.setEmpresaOferta(true);
		} else {
			stockOffer.setInvestidor(investor);
		}
		stockOffer.setAcao(stock);
		
		// Verifica se possui alguma oferta no mesmo valor
		Set<AcaoDemanda> stockDemands = stock.getDemandas();
		stockDemands.stream().forEach((stockDemand) -> {
			int quant = 0;
			if (stockDemand.getPreco() == stockOffer.getPreco() && stockDemand.getQuantidade() - stockDemand.getQuantidadeComprador() > 0) {
				if (stockOffer.getQuantidade() - stockOffer.getQuantidadeVendido()> stockDemand.getQuantidadeComprador() - stockDemand.getQuantidadeComprador()) {
					quant = stockDemand.getQuantidade() - stockDemand.getQuantidadeComprador();
				} else {
					quant = stockOffer.getQuantidade() - stockOffer.getQuantidadeVendido();
				}

				// Remove quantidade em ambos as ofertas
				if (quant > 0) {
					stockDemand.setQuantidadeComprador(stockDemand.getQuantidadeComprador() + quant);
					stockOffer.setQuantidadeVendido(stockOffer.getQuantidadeVendido() + quant);
					demandService.save(stockDemand);
					
					Mercado market = new Mercado();
					market.setQuantidade(quant);
					market.setPreco(stockDemand.getPreco());
					market.setOferta(stockOffer);
					market.setDemanda(stockDemand);
					marketService.save(market);
					
					if (stockOffer.isEmpresaOferta()) {
						stock.setQuantitidadeEmpresa(stock.getQuantitidadeEmpresa() - quant);
					}

					if (stockOffer.isEmpresaOferta()) {
						emailSender.SendEmail(stockOffer.getAcao().getCompany().getEmail(), 
								"Notificação de venda ação " + stockOffer.getAcao().getId(), 
								Integer.toString(quant) + " ações foram vendidas com sucesso no valor de " + stockDemand.getPreco() + " (preço unitário)."
						);
					} else {
						emailSender.SendEmail(stockOffer.getInvestidor().getEmail(), 
								"Notificação de venda ação " + stockOffer.getAcao().getId(), 
								Integer.toString(quant) + " ações foram vendidas com sucesso no valor de " + stockDemand.getPreco() + " (preço unitário)."
						);
					}
					emailSender.SendEmail(stockDemand.getInvestidor().getEmail(), 
							"Notificação de compra ação " + stockOffer.getAcao().getId(), 
							Integer.toString(quant) + " ações foram compradas com sucesso no valor de " + stockDemand.getPreco() + " (preço unitário)."
					);
				}
			}
		});
		offerRepository.save(stockOffer);
		
		Set<AcaoOferta> sOffers = stock.getOfertas();
		sOffers.add(stockOffer);
		stock.setOfertas(sOffers);
		stockService.save(stock.getId(), stock);

		if (!stockMarket.isEmpresaOferta()) {
			Set<AcaoOferta> iDemands = investor.getOfertas();
			iDemands.add(stockOffer);
			investor.setOfertas(iDemands);
			investorService.save(investor.getId(), investor);
		}

		return stockOffer;
	}

	@Override
	public AcaoOferta save(AcaoOferta offer) {
		offer.setId(offer.getId());
		return offerRepository.save(offer);
	}

}
