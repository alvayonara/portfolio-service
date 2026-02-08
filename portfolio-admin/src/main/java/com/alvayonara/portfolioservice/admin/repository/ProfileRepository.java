package com.alvayonara.portfolioservice.admin.repository;

import com.alvayonara.portfolioservice.admin.entity.Profile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProfileRepository extends ReactiveCrudRepository<Profile, Long> {
}
