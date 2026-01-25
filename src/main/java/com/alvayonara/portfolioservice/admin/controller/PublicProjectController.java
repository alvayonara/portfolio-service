package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.entity.Project;
import com.alvayonara.portfolioservice.admin.service.PublicProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/projects")
public class PublicProjectController {
    @Autowired
    private PublicProjectService projectService;

    @GetMapping
    public Flux<Project> list() {
        return projectService.listPublished();
    }

    @GetMapping("/{id}")
    public Mono<Project> get(@PathVariable Long id) {
        return projectService.getPublishedById(id);
    }
}
