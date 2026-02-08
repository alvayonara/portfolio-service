package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.dto.CreateUploadRequest;
import com.alvayonara.portfolioservice.admin.dto.PresignedUploadResponse;
import com.alvayonara.portfolioservice.admin.dto.UploadResponseDetail;
import com.alvayonara.portfolioservice.admin.entity.Profile;
import com.alvayonara.portfolioservice.admin.repository.ProfileRepository;
import com.alvayonara.portfolioservice.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Objects;

@Service
@Slf4j
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private S3Service s3Service;
    @Value("${aws.s3.profile-prefix}")
    private String profilePrefix;

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

    public Mono<UploadResponseDetail> createProfileImageUploadUrl(CreateUploadRequest request) {
        String s3Key = profilePrefix + "avatar";
        S3Service.PresignedUploadUrl uploadUrl = s3Service.generatePresignedUploadUrl(s3Key, request.contentType());
        return Mono.just(new UploadResponseDetail(uploadUrl.uploadUrl(), uploadUrl.publicUrl(), s3Key));
    }
}
