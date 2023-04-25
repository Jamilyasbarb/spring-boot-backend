package com.jamily.projetocursomc.service.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.jamily.projetocursomc.domain.Cliente;
import com.jamily.projetocursomc.dto.ClienteDTO;
import com.jamily.projetocursomc.repositories.ClienteRepository;
import com.jamily.projetocursomc.resources.exceptions.FieldMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public boolean isValid(ClienteDTO cliDto, ConstraintValidatorContext context) {
		// inclua os testes aqui, inserindo erros na lista
		
		@SuppressWarnings("unchecked")
		//pega variaveis do endpoint
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		//pega o id e converte para int
		Integer uriId = Integer.parseInt(map.get("id"));
		List<FieldMessage> listaErros = new ArrayList<>();
		
		//procura email
		Cliente aux = clienteRepository.findByEmail(cliDto.getEmail());
		
		//se o email existir e o id do cliente dele for diferente do "logado"
		if(aux != null && !aux.getId().equals(uriId)) {
			listaErros.add(new FieldMessage("email", "o email já existe"));
		}

		for (FieldMessage e : listaErros) {
			// inserindo error personalizados para a lista de erros do framework
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getFieldMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return listaErros.isEmpty();
	}
}