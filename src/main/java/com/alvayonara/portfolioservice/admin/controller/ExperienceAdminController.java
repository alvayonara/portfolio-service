package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.dto.PublicExperienceDto;
import com.alvayonara.portfolioservice.admin.entity.Experience;
import com.alvayonara.portfolioservice.admin.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/experiences")
@PreAuthorize("hasRole('ADMIN')")
public class ExperienceAdminController {
    @Autowired
    private ExperienceService experienceService;

    @PostMapping
    public Mono<Experience> create(@RequestBody Experience experience) {
        return experienceService.create(experience);
    }

    @GetMapping
    public Flux<PublicExperienceDto> list() {
        return experienceService.listAll();
    }

    @PutMapping("/{id}")
    public Mono<Experience> update(@PathVariable Long id, @RequestBody Experience experience) {
        return experienceService.update(id, experience);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return experienceService.delete(id);
    }
}
