package com.nhnacademy.couponapi.common.config;

import com.nhnacademy.couponapi.common.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/coupons/swagger-ui.html", "/coupons/swagger-ui/**", "/coupons/v3/api-docs/**").permitAll() // Swagger 접근 허용
                                .requestMatchers("/coupon/modal", "/coupons/books/*/coupons", "/coupons", "/coupons/info", "/coupons/expired").permitAll() // 비회원 접근 허용
                                .requestMatchers("/coupons/policy").authenticated() // 회원 접근 허용
                                .anyRequest().authenticated()); // 그 외 요청은 인증 필요
        return http.build();
    }
}