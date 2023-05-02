package com.jamily.projetocursomc.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.jamily.projetocursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);
	
}
