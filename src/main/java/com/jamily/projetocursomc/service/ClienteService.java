package com.jamily.projetocursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jamily.projetocursomc.domain.Cliente;
import com.jamily.projetocursomc.dto.ClienteDTO;
import com.jamily.projetocursomc.repositories.ClienteRepository;
import com.jamily.projetocursomc.service.exceptions.DataIntegrityException;
import com.jamily.projetocursomc.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente update(Cliente cliente) {
		//pega os dados do banco
		Cliente clienteBanco = find(cliente.getId());
		updateData(clienteBanco, cliente);
		return repo.save(clienteBanco);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, 
			String orderBy, String direction){
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage,  Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO cliDto) {
		return new Cliente(cliDto.getId(),cliDto.getNome(),cliDto.getEmail(),null,null);
	}
	
	private void updateData(Cliente clienteBanco, Cliente cliente) {
		//muda apeanas campos especificos
		clienteBanco.setNome(cliente.getNome());
		clienteBanco.setEmail(cliente.getEmail());
	}
}
