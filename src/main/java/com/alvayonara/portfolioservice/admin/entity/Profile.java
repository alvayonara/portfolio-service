package com.alvayonara.portfolioservice.admin.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@Table("profile")
public class Profile {
    @Id
    private Long id;
    private String fullName;
    private String headline;
    private String summary;
    private String location;
    private String email;
    private String githubUrl;
    private String linkedinUrl;
    private String s3Key;
    private Instant createdAt;
    private Instant updatedAt;
}
