package com.jamily.projetocursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jamily.projetocursomc.domain.Cidade;
import com.jamily.projetocursomc.dto.CidadeDTO;
import com.jamily.projetocursomc.service.CidadeService;

@RestController
@RequestMapping(value="/cidades")
public class CidadeResource {
	
	@Autowired
	private CidadeService cidadeService;

	@RequestMapping(value="/estado/{estadoId}",method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findByEstado(@PathVariable Integer estadoId){
		List<Cidade> listaCidades = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listaCidadeDto = listaCidades.stream().map(x -> new CidadeDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaCidadeDto);
		
	}
}
