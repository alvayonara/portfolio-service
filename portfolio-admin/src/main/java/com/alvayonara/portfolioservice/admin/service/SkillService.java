package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.entity.Skill;
import com.alvayonara.portfolioservice.admin.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Skill> create(Skill skill) {
        skill.setCreatedAt(Instant.now());
        skill.setUpdatedAt(Instant.now());
        return skillRepository.save(skill);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Skill> update(Long id, Skill skill) {
        return skillRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Skill not found")))
                .flatMap(sk -> {
                    sk.setName(skill.getName());
                    sk.setLevel(skill.getLevel());
                    sk.setUpdatedAt(Instant.now());
                    return skillRepository.save(sk);
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> delete(Long id) {
        return skillRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Flux<Skill> listByGroup(Long groupId) {
        return skillRepository.findBySkillGroupId(groupId);
    }
}
