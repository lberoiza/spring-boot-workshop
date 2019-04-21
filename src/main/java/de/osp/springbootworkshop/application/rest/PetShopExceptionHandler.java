package de.osp.springbootworkshop.application.rest;

import de.osp.springbootworkshop.application.rest.model.ApiError;
import de.osp.springbootworkshop.domain.service.PetShopApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Denny
 */
@ControllerAdvice
public class PetShopExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * {@link HttpStatus#BAD_REQUEST}
     * @param e
     * @return
     */
    @ExceptionHandler(PetShopApiException.class)
    public ResponseEntity<Object> handle(PetShopApiException e) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
