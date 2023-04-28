package com.jamily.projetocursomc.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.jamily.projetocursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage mailMessage = prepareSimpleMainMessageFromPedido(pedido);
		sendEmail(mailMessage);
	}

	protected SimpleMailMessage prepareSimpleMainMessageFromPedido(Pedido pedido) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		//quem esta recebendo
		mailMessage.setTo(pedido.getCliente().getEmail());
		
		//quem esta enviando
		mailMessage.setFrom(sender);
		
		//Qual o assunto do email
		mailMessage.setSubject("Pedido Confirmado! Código: " + pedido.getId());
		
		// A data
		mailMessage.setSentDate(new Date(System.currentTimeMillis()));
		
		//o corpo do email
		mailMessage.setText(pedido.toString());
		return mailMessage;
	}
}
