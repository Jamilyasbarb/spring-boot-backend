package com.jamily.projetocursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jamily.projetocursomc.domain.Cliente;
import com.jamily.projetocursomc.domain.enums.TipoCliente;
import com.jamily.projetocursomc.dto.ClienteNewDTO;
import com.jamily.projetocursomc.repositories.ClienteRepository;
import com.jamily.projetocursomc.resources.exceptions.FieldMessage;
import com.jamily.projetocursomc.service.validation.utils.BR;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public boolean isValid(ClienteNewDTO cliDto, ConstraintValidatorContext context) {
		// inclua os testes aqui, inserindo erros na lista
		List<FieldMessage> listaErros = new ArrayList<>();
		if(cliDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())&& !BR.isValidCPF(cliDto.getCpfOuCnpj())) {
			listaErros.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if(cliDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())&& !BR.isValidCNPJ(cliDto.getCpfOuCnpj())) {
			listaErros.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = clienteRepository.findByEmail(cliDto.getEmail());
		
		if(aux != null) {
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