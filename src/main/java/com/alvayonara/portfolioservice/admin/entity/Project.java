package com.alvayonara.portfolioservice.admin.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table("projects")
public class Project {
    @Id
    private Long id;
    private String title;
    private String description;
    private String techStack;
    private String repoUrl;
    private Instant createdAt;
    private Instant updatedAt;
}