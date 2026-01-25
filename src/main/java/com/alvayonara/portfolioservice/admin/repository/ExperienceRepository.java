package com.alvayonara.portfolioservice.admin.repository;

import com.alvayonara.portfolioservice.admin.entity.Experience;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ExperienceRepository extends ReactiveCrudRepository<Experience, Long> {
    Flux<Experience> findByPublishedTrueOrderByStartDateDesc();
}
