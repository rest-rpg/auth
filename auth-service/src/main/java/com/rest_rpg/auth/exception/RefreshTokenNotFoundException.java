package com.rest_rpg.auth.exception;

import org.openapitools.model.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RefreshTokenNotFoundException extends ResponseStatusException {

    public RefreshTokenNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCodes.REFRESH_TOKEN_NOT_FOUND.toString());
    }
}
