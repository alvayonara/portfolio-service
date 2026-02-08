package com.alvayonara.portfolioservice.dto;

import java.time.Instant;

public record PublicResumeDto(
        String url,
        Instant updatedAt
) {}
