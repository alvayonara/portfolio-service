package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.dto.CreateUploadRequest;
import com.alvayonara.portfolioservice.admin.dto.PresignedUploadResponse;
import com.alvayonara.portfolioservice.admin.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/resume")
public class ResumeAdminController {
    @Autowired
    private ResumeService resumeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload-url")
    public Mono<PresignedUploadResponse> createUploadUrl(@RequestBody Mono<CreateUploadRequest> request) {
        return request.flatMap(resumeService::createUploadUrl);
    }
}
