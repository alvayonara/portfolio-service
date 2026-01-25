package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.entity.Project;
import com.alvayonara.portfolioservice.admin.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Slf4j
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Project> create(Project project) {
        project.setCreatedAt(Instant.now());
        project.setUpdatedAt(Instant.now());
        return projectRepository.save(project)
                .doOnSuccess(p -> log.debug("Project created with id {}", p.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Project> getById(Long id) {
        return projectRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Project not found")));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Flux<Project> getAll() {
        return projectRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Project> update(Long id, Project project) {
        return getById(id)
                .flatMap(existProject -> {
                    existProject.setTitle(project.getTitle());
                    existProject.setDescription(project.getDescription());
                    existProject.setTechStack(project.getTechStack());
                    existProject.setRepoUrl(project.getRepoUrl());
                    existProject.setUpdatedAt(Instant.now());
                    log.info("Update project id {}", id);
                    return projectRepository.save(existProject);
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> delete(Long id) {
        return projectRepository.deleteById(id);
    }
}
