package com.rest_rpg.auth.starter.model;

import com.ms.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuth implements UserDetails {

    private UUID id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private Role role;

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
