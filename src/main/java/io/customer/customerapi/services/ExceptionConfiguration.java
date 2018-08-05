package io.customer.customerapi.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionConfiguration {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public void handleResourceNotFoundException() {
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ServerException.class)
	public void handleServerException() {
	}
}