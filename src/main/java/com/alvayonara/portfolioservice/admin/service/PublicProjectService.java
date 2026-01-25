package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.dto.PublicProjectDto;
import com.alvayonara.portfolioservice.admin.entity.Project;
import com.alvayonara.portfolioservice.admin.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PublicProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Flux<PublicProjectDto> listPublished() {
        return projectRepository.findByPublishedTrue().map(this::toDto);
    }

    public Mono<PublicProjectDto> getPublishedById(Long id) {
        return projectRepository.findById(id)
                .filter(Project::getPublished)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found")))
                .map(this::toDto);
    }

    private PublicProjectDto toDto(Project project) {
        return new PublicProjectDto(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getTechStack(),
                project.getRepoUrl()
        );
    }
}
