package com.proyect.task.config.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class JwtException extends RuntimeException {

    public JwtException(String message) {
        super(message);
    }
}
