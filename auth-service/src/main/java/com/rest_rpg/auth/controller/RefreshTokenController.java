package com.rest_rpg.auth.controller;

import com.rest_rpg.auth.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.RefreshTokenApi;
import org.openapitools.model.AuthenticationResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class RefreshTokenController implements RefreshTokenApi {

    private final RefreshTokenService refreshTokenService;
    private final HttpServletResponse httpServletResponse;

    @Override
    public ResponseEntity<AuthenticationResponse> refreshToken(String jwt) {
        return ResponseEntity.ok(refreshTokenService.refreshToken(jwt));
    }

    @Override
    public ResponseEntity<Void> logoutUser(String jwt) {
        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, refreshTokenService.logout(jwt).toString());
        return ResponseEntity.ok().build();
    }
}
