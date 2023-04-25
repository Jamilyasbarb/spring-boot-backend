package com.jamily.projetocursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jamily.projetocursomc.domain.Categoria;
import com.jamily.projetocursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias ")
	Page<Produto> findDistincByNomeContainingAndCategoriasIN(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

}
