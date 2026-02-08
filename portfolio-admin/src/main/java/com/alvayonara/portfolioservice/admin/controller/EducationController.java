package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.entity.Education;
import com.alvayonara.portfolioservice.admin.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/educations")
public class EducationController {
    @Autowired
    private EducationService educationService;

    @GetMapping
    public Flux<Education> list() {
        return educationService.list();
    }
}
