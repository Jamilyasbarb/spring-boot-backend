package com.jamily.projetocursomc.service;

import org.springframework.mail.SimpleMailMessage;

import com.jamily.projetocursomc.domain.Pedido;

import jakarta.mail.internet.MimeMessage;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);
	
}
