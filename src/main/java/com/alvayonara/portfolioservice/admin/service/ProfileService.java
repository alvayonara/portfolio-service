package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.entity.Profile;
import com.alvayonara.portfolioservice.admin.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Objects;

@Service
@Slf4j
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Mono<Profile> get() {
        return profileRepository.findById(1L).switchIfEmpty(Mono.just(new Profile()));
    }

    public Mono<Profile> save(Profile profile) {
        profile.setId(1L);
        profile.setUpdatedAt(Instant.now());
        if (Objects.isNull(profile.getCreatedAt())) {
            profile.setCreatedAt(Instant.now());
        }
        return profileRepository.save(profile)
                .doOnSuccess(p -> log.info("Profile updated"));
    }


}
