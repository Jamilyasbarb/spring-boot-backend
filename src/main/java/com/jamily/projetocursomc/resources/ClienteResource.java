package com.jamily.projetocursomc.resources;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jamily.projetocursomc.domain.Cliente;
import com.jamily.projetocursomc.dto.ClienteDTO;
import com.jamily.projetocursomc.dto.ClienteNewDTO;
import com.jamily.projetocursomc.service.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO cliDto,@PathVariable Integer id){
		Cliente cliente = service.fromDTO(cliDto);
		cliente.setId(id);
		cliente = service.update(cliente);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> listaCat = service.findAll();
		List<ClienteDTO> listaCatDto = listaCat.stream().map(elemento -> new 
				ClienteDTO(elemento)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listaCatDto);
	}
	
	@RequestMapping(value="/page",method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome")String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")String direction) {
		Page<Cliente> listaCat = service.findPage(page, linesPerPage,orderBy,direction);
		Page<ClienteDTO> listaCatDto = listaCat.map(elemento -> new 
				ClienteDTO(elemento));  
		return ResponseEntity.ok().body(listaCatDto);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void>insert(@Valid @RequestBody ClienteNewDTO cliDto){
		Cliente cli = service.fromDTO(cliDto);
		cli = service.insert(cli);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(cli.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}