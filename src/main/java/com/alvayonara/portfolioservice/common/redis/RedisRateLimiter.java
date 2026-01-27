package com.alvayonara.portfolioservice.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RedisRateLimiter {
    private static final int LIMIT = 5;
    private static final int WINDOW_SECONDS = 60;
    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisScript<Long> script;

    public Mono<Void> checkLimit(String ip) {
        String key = "contact:rl:" + ip;
        return redisTemplate.execute(script, List.of(key), String.valueOf(LIMIT), String.valueOf(WINDOW_SECONDS))
                .single()
                .flatMap(result -> {
                    if (result == 0) {
                        return Mono.error(new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests"));
                    }
                    return Mono.empty();
                });
    }
}
