package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.entity.Education;
import com.alvayonara.portfolioservice.admin.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class EducationService {
    @Autowired
    private EducationRepository educationRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Education> create(Education education) {
        education.setCreatedAt(Instant.now());
        education.setUpdatedAt(Instant.now());
        return educationRepository.save(education);
    }

    public Flux<Education> list() {
        return educationRepository.findAllByOrderByOrderIndexAsc();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Education> update(Long id, Education education) {
        return educationRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Education not found!")))
                .flatMap(edu -> {
                    edu.setInstitutionName(education.getInstitutionName());
                    edu.setDegree(education.getDegree());
                    edu.setFieldOfStudy(education.getFieldOfStudy());
                    edu.setStartYear(education.getStartYear());
                    edu.setEndYear(education.getEndYear());
                    edu.setDescription(education.getDescription());
                    edu.setOrderIndex(education.getOrderIndex());
                    edu.setUpdatedAt(Instant.now());
                    return educationRepository.save(edu);
                });
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Void> delete(Long id) {
        return educationRepository.deleteById(id);
    }
}
