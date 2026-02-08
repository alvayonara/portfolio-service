package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.entity.SkillGroup;
import com.alvayonara.portfolioservice.admin.repository.SkillGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class SkillGroupService {
    @Autowired
    private SkillGroupRepository skillGroupRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<SkillGroup> create(SkillGroup skillGroup) {
        skillGroup.setCreatedAt(Instant.now());
        skillGroup.setUpdatedAt(Instant.now());
        return skillGroupRepository.save(skillGroup);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<SkillGroup> update(Long id, SkillGroup skillGroup) {
        return skillGroupRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Group not found")))
                .flatMap(skillGr -> {
                    skillGr.setName(skillGroup.getName());
                    skillGr.setUpdatedAt(Instant.now());
                    return skillGroupRepository.save(skillGr);
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> delete(Long id) {
        return skillGroupRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Flux<SkillGroup> listAll() {
        return skillGroupRepository.findAll();
    }
}
