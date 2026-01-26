package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.entity.SkillGroup;
import com.alvayonara.portfolioservice.admin.service.SkillGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/skill-groups")
@PreAuthorize("hasRole('ADMIN')")
public class SkillGroupAdminController {
    @Autowired
    private SkillGroupService skillGroupService;

    @PostMapping
    public Mono<SkillGroup> create(@RequestBody SkillGroup skillGroup) {
        return skillGroupService.create(skillGroup);
    }

    @PutMapping("/{id}")
    public Mono<SkillGroup> update(@PathVariable Long id, @RequestBody SkillGroup skillGroup) {
        return skillGroupService.update(id, skillGroup);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return skillGroupService.delete(id);
    }

    @GetMapping
    public Flux<SkillGroup> list() {
        return skillGroupService.listAll();
    }
}
