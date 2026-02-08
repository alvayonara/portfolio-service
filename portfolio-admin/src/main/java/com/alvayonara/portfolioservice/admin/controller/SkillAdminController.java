package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.entity.Skill;
import com.alvayonara.portfolioservice.admin.service.SkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/skills")
@PreAuthorize("hasRole('ADMIN')")
public class SkillAdminController {
    @Autowired
    private SkillService skillService;

    @PostMapping
    public Mono<Skill> create(@RequestBody Skill skill) {
        return skillService.create(skill);
    }

    @PutMapping("/{id}")
    public Mono<Skill> update(@PathVariable Long id, @RequestBody Skill skill) {
        return skillService.update(id, skill);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return skillService.delete(id);
    }

    @GetMapping("/group/{groupId}")
    public Flux<Skill> list(@PathVariable Long groupId) {
        return skillService.listByGroup(groupId);
    }
}
