package com.alvayonara.portfolioservice.admin.dto;

import java.time.Instant;

public record PublicResumeDto(
        String url,
        Instant updatedAt
) {}
