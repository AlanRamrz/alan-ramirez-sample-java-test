package com.alanramrz.configuration.exceptions;

import com.alanramrz.utils.Constants;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomResponseException extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(it -> it.getField() + ": " + it.getDefaultMessage()).collect(Collectors.toList());
        String message = String.join(", ", errors);
        BaseError baseError = new BaseError(HttpStatus.BAD_REQUEST, Constants.REQUIRED_FIELDS, message);

        return super.handleExceptionInternal(ex, baseError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BaseError.class)
    public ResponseEntity<Object> handleBaseError(BaseError baseError){
        return ResponseEntity.status(baseError.getCode()).body(baseError);
    }
}
