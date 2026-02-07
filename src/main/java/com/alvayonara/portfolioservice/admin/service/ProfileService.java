package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.dto.CreateUploadRequest;
import com.alvayonara.portfolioservice.admin.dto.PresignedUploadResponse;
import com.alvayonara.portfolioservice.admin.dto.UploadResponseDetail;
import com.alvayonara.portfolioservice.admin.entity.Profile;
import com.alvayonara.portfolioservice.admin.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private S3Presigner s3Presigner;
    @Value("${aws.s3.bucket}")
    private String bucket;
    @Value("${aws.s3.profile-prefix}")
    private String profilePrefix;
    @Value("${aws.region}")
    private String region;

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
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .contentType(request.contentType())
                .build();
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putRequest)
                .signatureDuration(Duration.ofMinutes(10))
                .build();
        String uploadUrl = s3Presigner.presignPutObject(presignRequest).url().toString();
        String publicUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + s3Key;
        return Mono.just(new UploadResponseDetail(uploadUrl, publicUrl, s3Key));
    }
}
