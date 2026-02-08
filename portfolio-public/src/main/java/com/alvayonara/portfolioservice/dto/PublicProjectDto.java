package com.alvayonara.portfolioservice.dto;

public record PublicProjectDto(
        Long id,
        String title,
        String description,
        String techStack,
        String repoUrl,
        String s3Key
) {
}
