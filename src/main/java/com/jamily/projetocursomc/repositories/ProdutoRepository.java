package com.jamily.projetocursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jamily.projetocursomc.dominio.Categoria;
import com.jamily.projetocursomc.dominio.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
