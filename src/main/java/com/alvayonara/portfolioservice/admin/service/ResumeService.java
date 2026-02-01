package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.dto.CreateUploadRequest;
import com.alvayonara.portfolioservice.admin.dto.PresignedUploadResponse;
import com.alvayonara.portfolioservice.admin.entity.Resume;
import com.alvayonara.portfolioservice.admin.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class ResumeService {
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private S3Presigner s3Presigner;

    @Value("${aws.s3.bucket}")
    private String bucket;
    @Value("${aws.s3.resume-prefix}")
    private String resumePrefix;

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<PresignedUploadResponse> createUploadUrl(CreateUploadRequest request) {
        String s3Key = resumePrefix + UUID.randomUUID() + "-" + request.filename();
        Resume resume = Resume.builder()
                .s3Key(s3Key)
                .originalFilename(request.filename())
                .contentType(request.contentType())
                .size(request.size())
                .uploadedAt(Instant.now())
                .build();
        return resumeRepository.save(resume)
                .map(saved -> {
                    PutObjectRequest putRequest = PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(saved.getS3Key())
                            .contentType(saved.getContentType())
                            .build();
                    PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                            .putObjectRequest(putRequest)
                            .signatureDuration(Duration.ofMinutes(10))
                            .build();
                    String url = s3Presigner.presignPutObject(presignRequest).url().toString();
                    String publicUrl = "https://" + bucket + ".s3.amazonaws.com/" + s3Key;
                    return new PresignedUploadResponse(url, publicUrl);
                });
    }

    public Mono<String> generateDownloadUrl(Long resumeId) {
        return resumeRepository.findById(resumeId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found")))
                .map(resume -> {
                    GetObjectRequest getRequest = GetObjectRequest.builder()
                            .bucket(bucket)
                            .key(resume.getS3Key())
                            .responseContentDisposition("attachment; filename=\"" +
                                    resume.getOriginalFilename() + "\"")
                            .build();
                    GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                            .getObjectRequest(getRequest)
                            .signatureDuration(Duration.ofMinutes(10))
                            .build();
                    return s3Presigner.presignGetObject(presignRequest).url().toString();
                });
    }
}
