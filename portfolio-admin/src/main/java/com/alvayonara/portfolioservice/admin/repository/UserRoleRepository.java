package com.alvayonara.portfolioservice.admin.repository;

import com.alvayonara.portfolioservice.admin.entity.UserRole;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserRoleRepository extends ReactiveCrudRepository<UserRole, Long> {
    Flux<UserRole> findByUserId(Long userId);
}
