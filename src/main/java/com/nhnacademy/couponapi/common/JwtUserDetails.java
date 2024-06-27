package com.nhnacademy.couponapi.common;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
public record JwtUserDetails(Long userId, List<GrantedAuthority> roles) implements UserDetails {

    public static JwtUserDetails of(String userId, String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((new SimpleGrantedAuthority("ROLE_" + role)));

        return JwtUserDetails.builder()
            .userId(Long.parseLong(userId))
            .roles(authorities)
            .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return String.valueOf(userId);
    }
}
