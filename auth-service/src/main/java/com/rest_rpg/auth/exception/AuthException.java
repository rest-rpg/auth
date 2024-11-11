package com.rest_rpg.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthException extends ResponseStatusException {

    public AuthException() {
        super(HttpStatus.FORBIDDEN, "AUTH_EXCEPTION");
    }
}
