package com.jamily.projetocursomc.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jamily.projetocursomc.domain.Cliente;
import com.jamily.projetocursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage mailMessage = prepareSimpleMainMessageFromPedido(pedido);
		sendEmail(mailMessage);
	}

	protected SimpleMailMessage prepareSimpleMainMessageFromPedido(Pedido pedido) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setTo(pedido.getCliente().getEmail());// quem esta recebendo
		mailMessage.setFrom(sender);// quem esta enviando
		mailMessage.setSubject("Pedido Confirmado! Código: " + pedido.getId());// Qual o assunto do email
		mailMessage.setSentDate(new Date(System.currentTimeMillis()));// A data
		mailMessage.setText(pedido.toString());// o corpo do email
		
		return mailMessage;
	}
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage mailMessage = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(mailMessage);
	}

	private SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		mailMessage.setTo(cliente.getEmail());// quem esta recebendo
		mailMessage.setFrom(sender);// quem esta enviando
		mailMessage.setSubject("Solicitação de NOVA SENHA");// Qual o assunto do email
		mailMessage.setSentDate(new Date(System.currentTimeMillis()));// A data
		mailMessage.setText("Nova Senha: " + newPass);// o corpo do email
		
		return mailMessage;
	}

	protected String htmlFromTemplatePedido(Pedido pedido) {
		Context context = new Context();
		context.setVariable("pedido", pedido);
		return templateEngine.process("email/confirmacaoPedido", context);
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		try {
			MimeMessage mimeMessage = prepareSimpleMimeMessageFromPedido(pedido);
			sendHtmlEmail(mimeMessage);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(pedido);
		}
	}

	protected MimeMessage prepareSimpleMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(pedido.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirma! Código: " + pedido.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(pedido), true);
		return mimeMessage;
	};
}
