package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.entity.Education;
import com.alvayonara.portfolioservice.admin.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/educations")
@PreAuthorize("hasRole('ADMIN')")
public class EducationAdminController {
    @Autowired
    private EducationService educationService;

    @PostMapping
    public Mono<Education> create(@RequestBody Education education) {
        return educationService.create(education);
    }

    @PutMapping("/{id}")
    public Mono<Education> update(@PathVariable Long id, @RequestBody Education education) {
        return educationService.update(id, education);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return educationService.delete(id);
    }
}
