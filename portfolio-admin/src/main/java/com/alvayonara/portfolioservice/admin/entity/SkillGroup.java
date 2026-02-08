package com.alvayonara.portfolioservice.admin.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table("skill_group")
public class SkillGroup {
    @Id
    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
}
