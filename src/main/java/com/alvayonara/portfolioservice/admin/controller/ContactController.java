package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.dto.ContactRequest;
import com.alvayonara.portfolioservice.admin.service.ContactService;
import com.alvayonara.portfolioservice.common.redis.RedisRateLimiter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private RedisRateLimiter redisRateLimiter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Void>> submit(@Valid @RequestBody ContactRequest contactRequest, ServerHttpRequest httpRequest) {
        if (contactRequest.company() != null && !contactRequest.company().isBlank()) {
            return Mono.empty();
        }
        String ip = Optional.of(httpRequest.getHeaders().getFirst("X-Forwarded-For"))
                .map(x -> x.split(",")[0])
                .orElseGet(() -> httpRequest.getRemoteAddress().getAddress().getHostAddress());
        return redisRateLimiter.checkLimit(ip)
                .then(contactService.send(contactRequest))
                .thenReturn(ResponseEntity.accepted().build());
    }
}
