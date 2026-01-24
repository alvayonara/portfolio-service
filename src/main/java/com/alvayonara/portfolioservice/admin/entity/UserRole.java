package com.alvayonara.portfolioservice.admin.entity;

import org.springframework.data.relational.core.mapping.Table;

@Table("user_roles")
public record UserRole(
        Long userId,
        String role
) {
}
