package com.algaworks.algalog.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algalog.exception.Exception.Campo;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Campo> campos = new ArrayList<>();
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError)error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			campos.add(new Campo(nome, mensagem));
		}
		
		Exception exception = new Exception();
		exception.setStatus(status.value());
		exception.setDataHora(LocalDateTime.now());
		exception.setTitulo("Um ou mais campos estão inválido!");
		exception.setCampo(campos);
		
	
		return handleExceptionInternal(ex, exception, headers, status, request);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Exception exception = new Exception();
		exception.setStatus(status.value());
		exception.setDataHora(LocalDateTime.now());
		exception.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);
	}
	
}
