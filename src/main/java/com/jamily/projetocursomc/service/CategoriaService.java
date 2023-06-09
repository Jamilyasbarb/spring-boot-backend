package com.jamily.projetocursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jamily.projetocursomc.domain.Categoria;
import com.jamily.projetocursomc.dto.CategoriaDTO;
import com.jamily.projetocursomc.repositories.CategoriaRepository;
import com.jamily.projetocursomc.service.exceptions.DataIntegrityException;
import com.jamily.projetocursomc.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria>obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repo.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		//pega os dados do banco
		Categoria categoriaBanco = find(categoria.getId());
		updateData(categoriaBanco, categoria);
		return repo.save(categoriaBanco);
	}

	private void updateData(Categoria categoriaBanco, Categoria categoria) {
		//muda apeanas campos especificos
		categoriaBanco.setNome(categoria.getNome());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, 
			String orderBy, String direction){
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage,  Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO(CategoriaDTO catDto) {
		return new Categoria(catDto.getId(), catDto.getNome());
	}
}
