package com.alvayonara.portfolioservice.admin.repository;

import com.alvayonara.portfolioservice.admin.entity.Project;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProjectRepository extends ReactiveCrudRepository<Project, Long> {
    Flux<Project> findByPublishedTrue();
}
