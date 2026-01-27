package com.alvayonara.portfolioservice.admin.dto;

public record PresignedUploadResponse(
        Long resumeId,
        String uploadUrl
) {
}
