package com.jamily.projetocursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jamily.projetocursomc.dominio.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{

}
