package com.rest_rpg.auth.controller;

import com.rest_rpg.auth.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.AuthenticationApi;
import org.openapitools.model.AuthenticationRequest;
import org.openapitools.model.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService service;
    private final HttpServletResponse httpServletResponse;

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(service.authenticate(authenticationRequest, httpServletResponse));
    }
}
