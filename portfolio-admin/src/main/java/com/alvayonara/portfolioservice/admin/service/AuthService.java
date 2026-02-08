package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.entity.UserRole;
import com.alvayonara.portfolioservice.admin.repository.UserRepository;
import com.alvayonara.portfolioservice.admin.repository.UserRoleRepository;
import com.alvayonara.portfolioservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public Mono<String> login(String username, String password) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
                .flatMap(user -> {
                    if (!user.enabled()) {
                        return Mono.error(new RuntimeException("User disabled"));
                    }
                    if (!passwordEncoder.matches(password, user.password())) {
                        return Mono.error(new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Invalid username or password"
                        ));
                    }
                    return userRoleRepository.findByUserId(user.id())
                            .map(UserRole::role)
                            .collectList()
                            .map(roles -> jwtUtil.generateToken(user.username(), roles));
                });
    }
}
