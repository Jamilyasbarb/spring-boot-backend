package com.jamily.projetocursomc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jamily.projetocursomc.domain.Estado;
import com.jamily.projetocursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> findByName() {
		return estadoRepository.findAllByOrderByNome();
	}
}
