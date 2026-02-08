package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.dto.AuthRequest;
import com.alvayonara.portfolioservice.admin.dto.AuthResponse;
import com.alvayonara.portfolioservice.admin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Mono<AuthResponse> login(@RequestBody AuthRequest request) {
        return authService.login(request.username(), request.password()).map(AuthResponse::new);
    }
}
