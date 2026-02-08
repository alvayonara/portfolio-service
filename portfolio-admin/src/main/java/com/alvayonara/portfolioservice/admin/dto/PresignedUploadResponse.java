package com.alvayonara.portfolioservice.admin.dto;

public record PresignedUploadResponse(
        String uploadUrl,
        String publicUrl
) {
}
