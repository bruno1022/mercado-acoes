package com.javaee.bruno.mercadoacoes.comparators;

import java.util.Comparator;

import com.javaee.bruno.mercadoacoes.domain.AcaoDemanda;

public class AcaoDemandaComparatorByAcaoTimestamp implements Comparator<AcaoDemanda> {

	@Override
	public int compare(AcaoDemanda d1, AcaoDemanda d2) {
		return d1.getAcao().getId().compareTo(d2.getAcao().getId()) & d1.getTimestamp().compareTo(d2.getTimestamp());
	}

}
