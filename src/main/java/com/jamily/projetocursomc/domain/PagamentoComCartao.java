package com.jamily.projetocursomc.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jamily.projetocursomc.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento{
	
	private Integer numeroDeParcelas;
	private static final long serialVersionUID = 1L;
	
	public PagamentoComCartao() {
		
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estadoPag, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estadoPag, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
}
