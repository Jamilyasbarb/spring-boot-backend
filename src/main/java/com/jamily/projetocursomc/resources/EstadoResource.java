package com.jamily.projetocursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jamily.projetocursomc.domain.Estado;
import com.jamily.projetocursomc.dto.EstadoDTO;
import com.jamily.projetocursomc.service.EstadoService;

@RestController
@RequestMapping(value="estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService estadoService;	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll(){
		//recebendo lista do banoc
		List<Estado> listaEstado = estadoService.findByName();
		
		//filtrando campos especificos com o dto... Entao converte estado para estadoDTO
		List<EstadoDTO> listaEstadoDTO = listaEstado.stream().map(x -> new EstadoDTO(x)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listaEstadoDTO);
	}
}
