package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.dto.CreateUploadRequest;
import com.alvayonara.portfolioservice.admin.dto.PresignedUploadResponse;
import com.alvayonara.portfolioservice.admin.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/resume")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/upload-url")
    public Mono<PresignedUploadResponse> createUploadUrl(@RequestBody Mono<CreateUploadRequest> request) {
        return request.flatMap(resumeService::createUploadUrl);
    }

    @GetMapping("/{id}/download")
    public Mono<ResponseEntity<Void>> download(@PathVariable Long id) {
        return resumeService.generateDownloadUrl(id)
                .map(url -> ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build());
    }
}
