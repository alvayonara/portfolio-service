package com.alvayonara.portfolioservice.admin.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table("skill")
public class Skill {
    @Id
    private Long id;
    private Long skillGroupId;
    private String name;
    private String level;
    private Integer displayOrder;
    private Boolean published;
    private Instant createdAt;
    private Instant updatedAt;
}
