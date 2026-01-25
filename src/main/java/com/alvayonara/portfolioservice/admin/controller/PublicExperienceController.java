package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.entity.Experience;
import com.alvayonara.portfolioservice.admin.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/experiences")
public class PublicExperienceController {
    @Autowired
    private ExperienceService experienceService;

    @GetMapping
    public Flux<Experience> list() {
        return experienceService.listPublic();
    }
}
