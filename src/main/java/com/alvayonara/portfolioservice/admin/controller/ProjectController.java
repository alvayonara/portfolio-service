package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.entity.Project;
import com.alvayonara.portfolioservice.admin.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public Mono<Project> create(@RequestBody Project project) {
        return projectService.create(project);
    }

    @GetMapping("/{id}")
    public Mono<Project> get(@PathVariable Long id) {
        return projectService.getById(id);
    }

    @GetMapping
    public Flux<Project> list() {
        return projectService.getAll();
    }

    @PutMapping("/{id}")
    public Mono<Project> update(@PathVariable Long id, @RequestBody Project project) {
        return projectService.update(id, project);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return projectService.delete(id);
    }
}
