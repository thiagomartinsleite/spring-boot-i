package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //Interceptador de erro
public class ErroDeValiadacaoHandler {

	@Autowired
	private MessageSource messageSource; // Classe para pegar erro do spring
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST) // define a responsta quando interceptar o erro
	@ExceptionHandler(MethodArgumentNotValidException.class) // Para a classe de excepetion de argumento para a nota��o
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
		List<ErroDeFormularioDto> dto = new  ArrayList<ErroDeFormularioDto>();
		
		List<FieldError> fildErros = exception.getBindingResult().getFieldErrors();
		
		for (FieldError fieldError : fildErros) {
		    String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()); // Pega a mensagem
			ErroDeFormularioDto erro = new ErroDeFormularioDto(fieldError.getField(), mensagem); // Passa o codigo e o erro
			dto.add(erro);
		}
		return dto;
	}

}
