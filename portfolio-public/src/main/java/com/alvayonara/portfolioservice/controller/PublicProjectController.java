package com.alvayonara.portfolioservice.controller;

import com.alvayonara.portfolioservice.admin.dto.PublicProjectDto;
import com.alvayonara.portfolioservice.admin.entity.Project;
import com.alvayonara.portfolioservice.admin.service.PublicProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/projects")
public class PublicProjectController {
    @Autowired
    private PublicProjectService projectService;

    @GetMapping
    public Flux<PublicProjectDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        size = Math.min(size, 20);
        return projectService.listPublished(page, size);
    }

    @GetMapping("/{id}")
    public Mono<PublicProjectDto> get(@PathVariable Long id) {
        return projectService.getPublishedById(id);
    }
}
