package com.rest_rpg.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RefreshTokenNotFoundException extends ResponseStatusException {

    public RefreshTokenNotFoundException() {
        super(HttpStatus.NOT_FOUND, "REFRESH_TOKEN_NOT_FOUND");
    }
}
