package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.dto.CreateUploadRequest;
import com.alvayonara.portfolioservice.admin.dto.PresignedUploadResponse;
import com.alvayonara.portfolioservice.admin.dto.PublicResumeDto;
import com.alvayonara.portfolioservice.admin.entity.Resume;
import com.alvayonara.portfolioservice.admin.repository.ResumeRepository;
import com.alvayonara.portfolioservice.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class ResumeService {
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private S3Service s3Service;

    @Value("${aws.s3.resume-prefix}")
    private String resumePrefix;

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<PresignedUploadResponse> createUploadUrl(CreateUploadRequest request) {
        String s3Key = resumePrefix + "document";
        S3Service.PresignedUploadUrl uploadUrl = s3Service.generatePresignedUploadUrl(s3Key, request.contentType());
        
        Resume resume = Resume.builder()
                .s3Key(s3Key)
                .originalFilename(request.filename())
                .contentType(request.contentType())
                .size(request.size())
                .uploadedAt(Instant.now())
                .build();
        
        return resumeRepository.deleteAll()
                .then(resumeRepository.save(resume))
                .thenReturn(new PresignedUploadResponse(uploadUrl.uploadUrl(), uploadUrl.publicUrl()));
    }

    public Mono<PublicResumeDto> getLatest() {
        return resumeRepository.findFirstByOrderByUploadedAtDesc()
                .map(resume -> new PublicResumeDto(
                        s3Service.generatePublicUrl(resume.getS3Key()),
                        resume.getUploadedAt()
                ));
    }
}
