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

		// quem esta recebendo
		mailMessage.setTo(pedido.getCliente().getEmail());

		// quem esta enviando
		mailMessage.setFrom(sender);

		// Qual o assunto do email
		mailMessage.setSubject("Pedido Confirmado! Código: " + pedido.getId());

		// A data
		mailMessage.setSentDate(new Date(System.currentTimeMillis()));

		// o corpo do email
		mailMessage.setText(pedido.toString());
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
