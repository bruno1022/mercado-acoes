package com.javaee.bruno.mercadoacoes.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.bruno.mercadoacoes.config.RabbitMQConfig;
import com.javaee.bruno.mercadoacoes.domain.Empresa;
import com.javaee.bruno.mercadoacoes.domain.Message;
import com.javaee.bruno.mercadoacoes.domain.MessageId;
import com.javaee.bruno.mercadoacoes.domain.Acao;
import com.javaee.bruno.mercadoacoes.domain.AcaoDemanda;
import com.javaee.bruno.mercadoacoes.domain.AcaoMercado;
import com.javaee.bruno.mercadoacoes.domain.AcaoOferta;
import com.javaee.bruno.mercadoacoes.repositories.AcaoRepository;

@Service
public class AcaoServiceImpl implements AcaoService {

	@Autowired
	private AcaoRepository acaoRepository;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private DemandaService demandaService;
	@Autowired
	private OfertaService ofertaService;
	
	private final RabbitTemplate rabbitTemplate;

	public AcaoServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
	}
	
	@Override
	public MessageId sendMessage(String source, AcaoMercado acaoMercado) {
		Message message = messageService.createNew(new Message(source, acaoMercado));
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_MESSAGES, message);
		return new MessageId(message.getId());
	}

	@Override
	public Set<Acao> getAll() {
		Set<Acao> acoes = new HashSet<>();
		acaoRepository.findAll().iterator().forEachRemaining(acoes::add);
		return acoes;
	}

	@Override
	public Acao getById(String id) {
		return getAcaoById(id);
	}

	private Acao getAcaoById(String id) {
		Optional<Acao> acaoOptional = acaoRepository.findById(id);

		if (!acaoOptional.isPresent()) {
			throw new IllegalArgumentException("Açao não localizada pelo ID de valor: " + id.toString());
		}

		return acaoOptional.get();
	}
	
	@Override
	public Acao createNew(Acao acao) {
		Acao acaoInd0;
		try {
			acaoInd0 = acaoRepository.findByNome(acao.getNome()).get(0);
		} catch (Exception e) {
			return acaoRepository.save(acao);
		}
		throw new IllegalArgumentException("stock already exists with ID: " + acaoInd0.getId());
	}

	@Override
	public Acao emitNew(String empresaId, Acao acao) {
		Empresa empresa = empresaService.getById(empresaId);
		acao.setCompany(empresa); 
		acao.setQuantitidadeEmpresa(acao.getQuantitidadeEmpresa());
		Set<Acao> acoes = empresa.getAcoes();
		acoes.add(acao);
		empresa.setAcoes(acoes);
		this.createNew(acao);
		empresaService.save(empresaId, empresa);

		AcaoMercado acaoMercado = new AcaoMercado();
		acaoMercado.setAcaoId(acao.getId());
		acaoMercado.setQuantidade(acao.getQuantitidade());
		acaoMercado.setPreco(acao.getPrecoInicial());
		acaoMercado.setEmpresaOferta(true);
		ofertaService.createNew(acaoMercado);

		return acao;
	}

	@Override
	public Acao save(String id, Acao acao) {
		acao.setId(id);
		return acaoRepository.save(acao);
	}

	@Override
	public void deleteById(String id) {
		acaoRepository.deleteById(id);
	}

	@Override
	public Set<AcaoDemanda> getAllDemandas() {
		return demandaService.getAll();
		
	    //Comparator<Person> comparator = Comparator.comparing(person -> person.name);
	    //comparator = comparator.thenComparing(Comparator.comparing(person -> person.age));
		//return null;
	}

	@Override
	public Set<AcaoOferta> getAllOfertas() {
		
		return null;
	}

}
