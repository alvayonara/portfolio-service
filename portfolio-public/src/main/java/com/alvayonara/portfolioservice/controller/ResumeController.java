package com.alvayonara.portfolioservice.controller;

import com.alvayonara.portfolioservice.admin.dto.PublicResumeDto;
import com.alvayonara.portfolioservice.admin.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/resume")
public class ResumeController {
    @Autowired
    private ResumeService resumeService;

    @GetMapping
    public Mono<PublicResumeDto> getLatest() {
        return resumeService.getLatest();
    }
}
