package com.rest_rpg.auth.exception;

import com.ms.auth.model.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class JwtExpiredException extends ResponseStatusException {

    public JwtExpiredException() {
        super(HttpStatus.FORBIDDEN, ErrorCodes.JWT_EXPIRED.toString());
    }
}
