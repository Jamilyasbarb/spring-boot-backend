package com.jamily.projetocursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jamily.projetocursomc.domain.Categoria;
import com.jamily.projetocursomc.domain.Produto;
import com.jamily.projetocursomc.repositories.CategoriaRepository;
import com.jamily.projetocursomc.repositories.ProdutoRepository;
import com.jamily.projetocursomc.service.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepositoty;

	public Produto find(Integer id) {
		Optional<Produto>obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, 
			String orderBy, String direction){
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage,  Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepositoty.findAllById(ids);
		return repo.findDistincByNomeContainingAndCategoriasIN(nome, categorias, pageRequest);
	}
}
