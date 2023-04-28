package com.jamily.projetocursomc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService{
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	//dados do email que estão no dev.properties
	@Autowired
	private MailSender mailSender;

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("enviando Email");
		mailSender.send(msg);
		LOG.info("email enviado");
		
	}

}
