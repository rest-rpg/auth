package com.rest_rpg.auth.service;

import com.rest_rpg.auth.exception.AuthException;
import com.rest_rpg.auth.model.dto.AuthenticationRequest;
import com.rest_rpg.auth.model.dto.AuthenticationResponse;
import com.rest_rpg.auth.starter.service.JwtService;
import com.rest_rpg.user.api.model.UserAuth;
import com.rest_rpg.user.feign.UserInternalClient;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class AuthenticationService {

    private final UserInternalClient userInternalClient;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(@NotNull AuthenticationRequest request,
                                               HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    ));
        } catch (AuthenticationException exception) {
            throw new AuthException();
        }

        UserAuth user = userInternalClient.getUserByUsername(request.getUsername());

        return createJwtResponse(user, response);
    }

    private AuthenticationResponse createJwtResponse(UserAuth user, HttpServletResponse response) {
        var jwtToken = jwtService.generateToken(user.getUsername());

        sendRefreshToken(user, response);

        return AuthenticationResponse.builder()
                .username(user.getUsername())
                .token(jwtToken)
                .role(user.getRole())
                .build();
    }

    private void sendRefreshToken(@NotNull UserAuth user, HttpServletResponse response) {
        ResponseCookie springCookie = refreshTokenService.createRefreshToken(user.getId());
        response.setHeader(HttpHeaders.SET_COOKIE, springCookie.toString());
    }
}
