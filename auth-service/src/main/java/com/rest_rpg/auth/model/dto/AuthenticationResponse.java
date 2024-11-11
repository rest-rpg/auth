package com.rest_rpg.auth.model.dto;

import com.rest_rpg.user.api.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @NotNull
    private String username;

    @NotNull
    private String token;

    @NotNull
    private Role role;
}
