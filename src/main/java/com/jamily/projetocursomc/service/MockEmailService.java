package com.jamily.projetocursomc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import jakarta.mail.internet.MimeMessage;

public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando o envio de Email");
		LOG.info(msg.toString());
		LOG.info("email enviado");
	}
	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando o envio de Email HTM");
		LOG.info(msg.toString());
		LOG.info("email enviado");
	}

}
