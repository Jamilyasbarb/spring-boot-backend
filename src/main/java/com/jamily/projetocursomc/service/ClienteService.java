package com.jamily.projetocursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jamily.projetocursomc.domain.Cidade;
import com.jamily.projetocursomc.domain.Cliente;
import com.jamily.projetocursomc.domain.Endereco;
import com.jamily.projetocursomc.domain.enums.Perfil;
import com.jamily.projetocursomc.domain.enums.TipoCliente;
import com.jamily.projetocursomc.dto.ClienteDTO;
import com.jamily.projetocursomc.dto.ClienteNewDTO;
import com.jamily.projetocursomc.repositories.ClienteRepository;
import com.jamily.projetocursomc.repositories.EnderecoRepository;
import com.jamily.projetocursomc.security.UserSS;
import com.jamily.projetocursomc.service.exceptions.AuthorizationException;
import com.jamily.projetocursomc.service.exceptions.DataIntegrityException;
import com.jamily.projetocursomc.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder bCrypt;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		repo.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return cliente;
	}

	public Cliente find(Integer id) {
		
		//usuario logado
		UserSS user = UserService.authenticated();
		
		if (user ==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
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
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
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
		return new Cliente(cliDto.getId(), cliDto.getNome(),cliDto.getEmail(),null,null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO cliDto) {
		Cliente cliente = new Cliente(null,cliDto.getNome(),cliDto.getEmail(),cliDto.getCpfOuCnpj(),TipoCliente.toEnum(cliDto.getTipo()), bCrypt.encode(cliDto.getSenha()));
		Cidade cidade = new Cidade(cliDto.getCidadeId(),null,null);
		Endereco end = new Endereco(null, cliDto.getLogradouro(),cliDto.getNumero(),cliDto.getComplemento(),cliDto.getBairro(),cliDto.getCep(),cliente, cidade);
		cliente.getEnderecos().add(end);
		cliente.getTelefones().add(cliDto.getTelefone1());
		if (cliDto.getTelefone2() != null) {
			cliente.getTelefones().add(cliDto.getTelefone2());
		}
		if (cliDto.getTelefone3() != null) {
			cliente.getTelefones().add(cliDto.getTelefone3());
		}
		return cliente;
	}
	
	private void updateData(Cliente clienteBanco, Cliente cliente) {
		//muda apeanas campos especificos
		clienteBanco.setNome(cliente.getNome());
		clienteBanco.setEmail(cliente.getEmail());
	}
}
