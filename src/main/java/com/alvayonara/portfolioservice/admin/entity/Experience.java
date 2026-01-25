package com.alvayonara.portfolioservice.admin.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Table("experience")
public class Experience {
    @Id
    private Long id;
    private String company;
    private String title;
    private String location;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean published;
    private Instant createdAt;
    private Instant updatedAt;
}
