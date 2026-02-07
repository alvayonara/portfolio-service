package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.dto.PublicProfileDto;
import com.alvayonara.portfolioservice.admin.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/profile")
public class PublicProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping
    public Mono<PublicProfileDto> get() {
        return profileRepository.findById(1L)
                .map(profile -> new PublicProfileDto(
                        profile.getFullName(),
                        profile.getHeadline(),
                        profile.getSummary(),
                        profile.getLocation(),
                        profile.getGithubUrl(),
                        profile.getLinkedinUrl(),
                        profile.getEmail(),
                        profile.getS3Key()
                ));
    }
}
