package com.rest_rpg.auth.exception;

import com.ms.auth.model.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthException extends ResponseStatusException {

    public AuthException() {
        super(HttpStatus.FORBIDDEN, ErrorCodes.AUTH_EXCEPTION.toString());
    }
}
