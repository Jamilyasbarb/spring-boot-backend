package com.jamily.projetocursomc.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jamily.projetocursomc.domain.Cliente;
import com.jamily.projetocursomc.repositories.ClienteRepository;
import com.jamily.projetocursomc.service.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (cliente == null ) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(bcrypt.encode(newPass));
		clienteRepository.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente,newPass);
	}

	private String newPassword() {
		char[] novaSenha = new char[10];
		for(int i =0; i<10; i++) {
			novaSenha[i] = randomChar();
		}
		
		return new String(novaSenha);
	}

	private char randomChar() {
		int caractereSorteado = random.nextInt();
		if (caractereSorteado == 0) { //gera um digito
			return (char) (random.nextInt(10) + 48);
		} else if(caractereSorteado == 1) {//gera uma letra maiuscula
			return (char) (random.nextInt(26) + 65);
		}else { //gera uma letra minuscula
			return (char) (random.nextInt(26) + 97);
		}
	}
}
