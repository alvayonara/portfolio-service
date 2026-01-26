package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.dto.PublicSkillGroupDto;
import com.alvayonara.portfolioservice.admin.service.PublicSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/skills")
public class PublicSkillController {
    @Autowired
    private PublicSkillService publicSkillService;

    @GetMapping
    public Flux<PublicSkillGroupDto> list() {
        return publicSkillService.listPublished();
    }
}
