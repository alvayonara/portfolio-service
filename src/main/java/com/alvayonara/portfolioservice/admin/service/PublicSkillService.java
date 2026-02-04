package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.dto.PublicSkillDto;
import com.alvayonara.portfolioservice.admin.dto.PublicSkillGroupDto;
import com.alvayonara.portfolioservice.admin.repository.SkillGroupRepository;
import com.alvayonara.portfolioservice.admin.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PublicSkillService {
    @Autowired
    private SkillGroupRepository skillGroupRepository;
    @Autowired
    private SkillRepository skillRepository;

    public Flux<PublicSkillGroupDto> listPublished() {
        return skillGroupRepository.findAll()
                .flatMap(skillGroup -> skillRepository.findBySkillGroupId(skillGroup.getId())
                        .map(skill -> new PublicSkillDto(skill.getName()))
                        .collectList()
                        .map(skills -> new PublicSkillGroupDto(skillGroup.getName(), skills)));
    }
}
