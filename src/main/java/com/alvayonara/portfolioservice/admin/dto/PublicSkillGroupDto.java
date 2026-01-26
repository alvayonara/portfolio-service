package com.alvayonara.portfolioservice.admin.dto;

import java.util.List;

public record PublicSkillGroupDto(
        String group,
        List<PublicSkillDto> skills
) {
}
