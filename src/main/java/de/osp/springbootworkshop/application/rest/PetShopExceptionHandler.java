package de.osp.springbootworkshop.application.rest;

import de.osp.springbootworkshop.application.rest.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Denny
 */
@ControllerAdvice
public class PetShopExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(PetShopApiException.class)
    public ResponseEntity<Object> handle(PetShopApiException e) {
        return new ResponseEntity<>(ApiError.of(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
