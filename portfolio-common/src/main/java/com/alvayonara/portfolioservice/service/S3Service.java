package com.alvayonara.portfolioservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
public class S3Service {
    
    private final S3Presigner s3Presigner;
    private final String bucket;
    private final String region;

    public S3Service(S3Presigner s3Presigner,
                    @Value("${aws.s3.bucket}") String bucket,
                    @Value("${aws.region}") String region) {
        this.s3Presigner = s3Presigner;
        this.bucket = bucket;
        this.region = region;
    }

    public PresignedUploadUrl generatePresignedUploadUrl(String s3Key, String contentType) {
        return generatePresignedUploadUrl(s3Key, contentType, Duration.ofMinutes(10));
    }

    public PresignedUploadUrl generatePresignedUploadUrl(String s3Key, String contentType, Duration duration) {
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .contentType(contentType)
                .build();
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putRequest)
                .signatureDuration(duration)
                .build();
        PresignedPutObjectRequest presigned = s3Presigner.presignPutObject(presignRequest);
        String uploadUrl = presigned.url().toString();
        String publicUrl = generatePublicUrl(s3Key);
        return new PresignedUploadUrl(uploadUrl, publicUrl);
    }

    public String generatePublicUrl(String s3Key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, s3Key);
    }

    public record PresignedUploadUrl(String uploadUrl, String publicUrl) {}
}
