package com.rest_rpg.auth.repository;

import com.rest_rpg.auth.model.RefreshToken;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(@NotEmpty String token);

    Optional<RefreshToken> findByUserId(long userId);
}
