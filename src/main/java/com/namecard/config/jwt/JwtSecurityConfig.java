package com.namecard.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtConfig jwtConfig;

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(
            new JwtAuthenticationFilter(jwtConfig),
            UsernamePasswordAuthenticationFilter.class
        );
    }
}
