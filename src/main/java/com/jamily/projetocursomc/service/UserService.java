package com.jamily.projetocursomc.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.jamily.projetocursomc.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			//retorna o usuario logado
			return (UserSS)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
