package com.alvayonara.portfolioservice.dto;

import java.time.LocalDate;

public record PublicExperienceDto(
        Long id,
        String company,
        String title,
        String location,
        String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
