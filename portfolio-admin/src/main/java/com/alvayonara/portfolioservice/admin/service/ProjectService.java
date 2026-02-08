package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.dto.CreateUploadRequest;
import com.alvayonara.portfolioservice.admin.dto.PresignedUploadResponse;
import com.alvayonara.portfolioservice.admin.entity.Project;
import com.alvayonara.portfolioservice.admin.repository.ProjectRepository;
import com.alvayonara.portfolioservice.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private S3Service s3Service;
    @Value("${aws.s3.project-prefix}")
    private String projectPrefix;

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Project> createDraft() {
        Project project = new Project();
        project.setPublished(false);
        project.setCreatedAt(Instant.now());
        project.setUpdatedAt(Instant.now());
        return projectRepository.save(project);
    }

    public Mono<PresignedUploadResponse> createProjectImageUploadUrl(Long projectId, CreateUploadRequest request) {
        return projectRepository.findById(projectId)
                .switchIfEmpty(Mono.error(new RuntimeException("Project not found")))
                .flatMap(project -> {
                    String s3Key = projectPrefix + projectId + "/" + UUID.randomUUID() + "-" + request.filename();
                    project.setS3Key(s3Key);
                    project.setUpdatedAt(Instant.now());
                    
                    S3Service.PresignedUploadUrl uploadUrl = s3Service.generatePresignedUploadUrl(s3Key, request.contentType());
                    return projectRepository.save(project)
                            .thenReturn(new PresignedUploadResponse(uploadUrl.uploadUrl(), uploadUrl.publicUrl()));
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Project> update(Long id, Project updated) {
        return projectRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Project not found")))
                .flatMap(p -> {
                    p.setTitle(updated.getTitle());
                    p.setDescription(updated.getDescription());
                    p.setTechStack(updated.getTechStack());
                    p.setRepoUrl(updated.getRepoUrl());
                    p.setUpdatedAt(Instant.now());
                    return projectRepository.save(p);
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Project> publish(Long id) {
        return togglePublish(id, true);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Project> unpublish(Long id) {
        return togglePublish(id, false);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Project> getById(Long id) {
        return projectRepository.findById(id).switchIfEmpty(Mono.error(new RuntimeException("Project not found")));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Flux<Project> getAll() {
        return projectRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> delete(Long id) {
        return projectRepository.deleteById(id);
    }

    private Mono<Project> togglePublish(Long id, boolean publish) {
        return projectRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Project not found")))
                .flatMap(p -> {
                    p.setPublished(publish);
                    p.setUpdatedAt(Instant.now());
                    return projectRepository.save(p);
                });
    }
}
