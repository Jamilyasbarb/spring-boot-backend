package com.jamily.projetocursomc.service;

import org.springframework.mail.SimpleMailMessage;

import com.jamily.projetocursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
}
