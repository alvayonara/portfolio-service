package com.alvayonara.portfolioservice.admin.repository;

import com.alvayonara.portfolioservice.admin.entity.Skill;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SkillRepository extends ReactiveCrudRepository<Skill, Long> {
    Flux<Skill> findByPublishedTrueOrderByDisplayOrderAsc(Long groupId);
    Flux<Skill> findBySkillGroupId(Long skillGroupId);
}
