package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.entity.Experience;
import com.alvayonara.portfolioservice.admin.repository.ExperienceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@Slf4j
public class ExperienceService {
    @Autowired
    private ExperienceRepository experienceRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Experience> create(Experience experience) {
        experience.setCreatedAt(Instant.now());
        experience.setUpdatedAt(Instant.now());
        return experienceRepository.save(experience);
    }

    public Flux<Experience> listPublic() {
        return experienceRepository.findByPublishedTrueOrderByStartDateDesc();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Flux<Experience> listAll() {
        return experienceRepository.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Experience> update(Long id, Experience experience) {
        return experienceRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Experience not found")))
                .flatMap(exp -> {
                    exp.setCompany(experience.getCompany());
                    exp.setTitle(experience.getTitle());
                    exp.setLocation(experience.getLocation());
                    exp.setDescription(experience.getDescription());
                    exp.setStartDate(experience.getStartDate());
                    exp.setEndDate(experience.getEndDate());
                    exp.setPublished(experience.getPublished());
                    exp.setUpdatedAt(Instant.now());
                    return experienceRepository.save(exp);
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> delete(Long id) {
        return experienceRepository.deleteById(id);
    }
}
