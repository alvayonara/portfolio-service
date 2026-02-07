package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.dto.CreateUploadRequest;
import com.alvayonara.portfolioservice.admin.dto.PresignedUploadResponse;
import com.alvayonara.portfolioservice.admin.dto.PublicResumeDto;
import com.alvayonara.portfolioservice.admin.entity.Resume;
import com.alvayonara.portfolioservice.admin.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
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
    @Value("${aws.region}")
    private String region;

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<PresignedUploadResponse> createUploadUrl(CreateUploadRequest request) {
        String s3Key = resumePrefix + UUID.randomUUID() + "-" + request.filename();
        return Mono.fromCallable(() -> {
                    PutObjectRequest putRequest = PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(s3Key)
                            .contentType(request.contentType())
                            .build();
                    PutObjectPresignRequest presignRequest =
                            PutObjectPresignRequest.builder()
                                    .putObjectRequest(putRequest)
                                    .signatureDuration(Duration.ofMinutes(10))
                                    .build();
                    return s3Presigner.presignPutObject(presignRequest);
                })
                .flatMap(presigned -> {
                    String uploadUrl = presigned.url().toString();
                    String publicUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + s3Key;
                    Resume resume = Resume.builder()
                            .s3Key(s3Key)
                            .originalFilename(request.filename())
                            .contentType(request.contentType())
                            .size(request.size())
                            .uploadedAt(Instant.now())
                            .build();
                    return resumeRepository.deleteAll()
                            .then(resumeRepository.save(resume))
                            .thenReturn(new PresignedUploadResponse(uploadUrl, publicUrl));
                });
    }

    public Mono<PublicResumeDto> getLatest() {
        return resumeRepository.findFirstByOrderByUploadedAtDesc()
                .map(resume -> new PublicResumeDto(
                        "https://" + bucket + ".s3.amazonaws.com/" + resume.getS3Key(),
                        resume.getUploadedAt()
                ));
    }
}
