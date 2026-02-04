package com.alvayonara.portfolioservice.admin.repository;

import com.alvayonara.portfolioservice.admin.entity.SkillGroup;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SkillGroupRepository extends ReactiveCrudRepository<SkillGroup, Long> {
}
