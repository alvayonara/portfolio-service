package com.alvayonara.portfolioservice.admin.repository;

import com.alvayonara.portfolioservice.admin.entity.Resume;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ResumeRepository extends ReactiveCrudRepository<Resume, Long> {
    Mono<Resume> findFirstByOrderByUploadedAtDesc();
}
