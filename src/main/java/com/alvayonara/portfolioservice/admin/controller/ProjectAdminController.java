package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.dto.CreateUploadRequest;
import com.alvayonara.portfolioservice.admin.dto.PresignedUploadResponse;
import com.alvayonara.portfolioservice.admin.entity.Project;
import com.alvayonara.portfolioservice.admin.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/projects")
@PreAuthorize("hasRole('ADMIN')")
public class ProjectAdminController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("/draft")
    public Mono<Project> createDraft() {
        return projectService.createDraft();
    }

    @PostMapping("/{id}/image/upload-url")
    public Mono<PresignedUploadResponse> createImageUploadUrl(@PathVariable Long id, @RequestBody CreateUploadRequest request) {
        return projectService.createProjectImageUploadUrl(id, request);
    }

    @PutMapping("/{id}")
    public Mono<Project> update(@PathVariable Long id, @RequestBody Project project) {
        return projectService.update(id, project);
    }

    @PostMapping("/{id}/publish")
    public Mono<Project> publish(@PathVariable Long id) {
        return projectService.publish(id);
    }

    @PostMapping("/{id}/unpublish")
    public Mono<Project> unpublish(@PathVariable Long id) {
        return projectService.unpublish(id);
    }

    @GetMapping("/{id}")
    public Mono<Project> get(@PathVariable Long id) {
        return projectService.getById(id);
    }

    @GetMapping
    public Flux<Project> list() {
        return projectService.getAll();
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return projectService.delete(id);
    }
}
