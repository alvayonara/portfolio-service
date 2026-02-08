package com.alvayonara.portfolioservice.dto;

import java.util.List;

public record PublicSkillGroupDto(
        String group,
        List<PublicSkillDto> skills
) {
}
