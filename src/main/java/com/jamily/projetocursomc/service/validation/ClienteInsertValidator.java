package com.jamily.projetocursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import com.jamily.projetocursomc.domain.enums.TipoCliente;
import com.jamily.projetocursomc.dto.ClienteNewDTO;
import com.jamily.projetocursomc.resources.exceptions.FieldMessage;
import com.jamily.projetocursomc.service.validation.utils.BR;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO cliDto, ConstraintValidatorContext context) {
		List<FieldMessage> listaErros = new ArrayList<>();
		if(cliDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())&& !BR.isValidCPF(cliDto.getCpfOuCnpj())) {
			listaErros.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if(cliDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())&& !BR.isValidCNPJ(cliDto.getCpfOuCnpj())) {
			listaErros.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		// inclua os testes aqui, inserindo erros na lista

		for (FieldMessage e : listaErros) {
			// inserindo error personalizados para a lista de erros do framework
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getFieldMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return listaErros.isEmpty();
	}
}