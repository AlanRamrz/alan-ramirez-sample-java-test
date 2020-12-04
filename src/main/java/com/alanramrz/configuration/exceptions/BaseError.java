package com.alanramrz.configuration.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.http.HttpStatus;

@JsonIgnoreProperties(value = {"cause", "stackTrace", "localizedMessage", "suppressed"})
@Data
public class BaseError extends Exception {
    private HttpStatus code;
    private String type;
    private String message;

    public BaseError(HttpStatus code, String type, String message){
        super(message);
        this.code = code;
        this.type = type;
        this.message = message;
    }
}
