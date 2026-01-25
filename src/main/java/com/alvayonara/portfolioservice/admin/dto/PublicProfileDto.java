package com.alvayonara.portfolioservice.admin.dto;

public record PublicProfileDto(
        String fullName,
        String headline,
        String summary,
        String location,
        String githubUrl,
        String linkedinUrl,
        String resumeUrl
) {
}
