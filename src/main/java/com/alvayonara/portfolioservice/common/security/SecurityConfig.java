package com.alvayonara.portfolioservice.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, ReactiveJwtDecoder jwtDecoder, ReactiveJwtAuthenticationConverter jwtAuthConverter) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/portfolio/**",
                                "/projects/**",
                                "/profile/**",
                                "/auth/**"
                        ).permitAll()
                        .pathMatchers("/admin/**").hasRole("ADMIN")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                        .jwtDecoder(jwtDecoder)
                        .jwtAuthenticationConverter(jwtAuthConverter)))
                .build();
    }
}
