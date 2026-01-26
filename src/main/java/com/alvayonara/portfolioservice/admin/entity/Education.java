package com.alvayonara.portfolioservice.admin.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table("education")
public class Education {
    @Id
    private Long id;
    private String institutionName;
    private String degree;
    private String fieldOfStudy;
    private Integer startYear;
    private Integer endYear;
    private String description;
    private Integer orderIndex;
    private Instant createdAt;
    private Instant updatedAt;
}
