package com.rest_rpg.auth.service;

import com.rest_rpg.auth.exception.JwtExpiredException;
import com.rest_rpg.auth.exception.RefreshTokenNotFoundException;
import com.rest_rpg.auth.starter.config.TokenProperties;
import com.rest_rpg.auth.model.RefreshToken;
import com.rest_rpg.auth.repository.RefreshTokenRepo;
import com.rest_rpg.auth.starter.service.JwtService;
import com.rest_rpg.user.api.model.UserLite;
import com.rest_rpg.user.feign.UserInternalClient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.AuthenticationResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Validated
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepository;
    private final UserInternalClient userInternalClient;
    private final JwtService jwtService;
    private final TokenProperties tokenProperties;

    public AuthenticationResponse refreshToken(@NotEmpty String jwt) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(jwt).orElseThrow(RefreshTokenNotFoundException::new);
        verifyExpiration(refreshToken);
        UserLite user = userInternalClient.getUserById(refreshToken.getUserId());
        String accessToken = jwtService.generateToken(user.username());

        return new AuthenticationResponse(user.username(), accessToken, user.role().toString());
    }

    public ResponseCookie logout(@NotEmpty String jwt) {
        var refreshToken = refreshTokenRepository.findByToken(jwt).orElseThrow(RefreshTokenNotFoundException::new);

        refreshToken.setExpiryDate(Instant.now().plusMillis(1000));
        refreshTokenRepository.save(refreshToken);

        return ResponseCookie
                .from(tokenProperties.getRefreshTokenCookieName(), "")
                .httpOnly(true)
                .path("/")
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .build();
    }

    public ResponseCookie createRefreshToken(long userId) {
        var refreshToken = refreshTokenRepository.findByUserId(userId).orElse(
                RefreshToken.builder().userId(userId).build()
        );

        refreshToken.setExpiryDate(Instant.now().plusMillis(tokenProperties.getRefreshTokenExpirationMs()));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);

        return ResponseCookie.from(tokenProperties.getRefreshTokenCookieName(), refreshToken.getToken())
                .httpOnly(true)
                .path("/")
                .secure(true)
                .sameSite("None")
                .maxAge(tokenProperties.getRefreshTokenExpirationMs() / 1000)
                .build();
    }

    private void verifyExpiration(@NotNull @Valid RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new JwtExpiredException();
        }
    }
}
