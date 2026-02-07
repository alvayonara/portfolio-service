package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.dto.CreateUploadRequest;
import com.alvayonara.portfolioservice.admin.dto.PresignedUploadResponse;
import com.alvayonara.portfolioservice.admin.entity.Profile;
import com.alvayonara.portfolioservice.admin.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/profile")
@PreAuthorize("hasRole('ADMIN')")
public class ProfileAdminController {
    @Autowired
    private ProfileService profileService;

    @GetMapping
    public Mono<Profile> get() {
        return profileService.get();
    }

    @PutMapping
    public Mono<Profile> update(@RequestBody Profile profile) {
        return profileService.save(profile);
    }

    @PostMapping("/{id}/image/upload-url")
    public Mono<PresignedUploadResponse> createImageUploadUrl(@RequestBody CreateUploadRequest request) {
        return profileService.createProfileImageUploadUrl(request);
    }
}
