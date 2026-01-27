package com.alvayonara.portfolioservice.admin.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Builder
@Table("resume")
public class Resume {
    @Id
    private Long id;
    private String s3Key;
    private String originalFilename;
    private String contentType;
    private Long size;
    private Instant uploadedAt;
}
