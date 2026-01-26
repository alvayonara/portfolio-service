package com.alvayonara.portfolioservice.admin.repository;

import com.alvayonara.portfolioservice.admin.entity.Education;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EducationRepository extends ReactiveCrudRepository<Education, Long> {
    Flux<Education> findAllByOrderByOrderIndexAsc();
}
