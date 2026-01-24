package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.dto.AuthRequest;
import com.alvayonara.portfolioservice.admin.dto.AuthResponse;
import com.alvayonara.portfolioservice.common.util.JwtUtil;
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
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Mono<AuthResponse> login(@RequestBody AuthRequest request) {
        // TODO: Temp logic, change later
        if (!"admin".equals(request.username()) || !"password".equals(request.password())) {
            return Mono.error(new RuntimeException("Invalid credentials"));
        }
        String token = jwtUtil.generateToken(request.username());
        return Mono.just(new AuthResponse(token));
    }
}
