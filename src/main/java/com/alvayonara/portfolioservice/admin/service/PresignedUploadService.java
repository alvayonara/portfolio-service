package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.dto.CreateUploadRequest;
import com.alvayonara.portfolioservice.admin.dto.PresignedAssetUploadResponse;
import com.alvayonara.portfolioservice.admin.dto.PresignedUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Service
public class PresignedUploadService {
    @Autowired
    private S3Presigner s3Presigner;
    @Value("${aws.s3.bucket}")
    private String bucket;

    public Mono<PresignedAssetUploadResponse> createUploadUrl(
            String prefix,
            CreateUploadRequest request
    ) {
        String key = prefix + UUID.randomUUID() + "-" + request.filename();
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(request.contentType())
                .build();
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putRequest)
                .signatureDuration(Duration.ofMinutes(10))
                .build();
        String uploadUrl = s3Presigner.presignPutObject(presignRequest).url().toString();
        String publicUrl = "https://" + bucket + ".s3.amazonaws.com/" + key;
        return Mono.just(
                new PresignedAssetUploadResponse(uploadUrl, publicUrl)
        );
    }
}
