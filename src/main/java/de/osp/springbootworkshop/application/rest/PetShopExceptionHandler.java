package de.osp.springbootworkshop.application.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PetShopExceptionHandler {
	
	@ExceptionHandler(PetShopApiException.class)
	public ResponseEntity<Object> handle(PetShopApiException e){
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	

}
