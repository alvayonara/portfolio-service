package com.alvayonara.portfolioservice.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactRequest(
        @NotBlank @Size(max = 100) String name,
        @Email @NotBlank String email,
        @Size(max = 200) String subject,
        @NotBlank String message,
        String company
) {
}
