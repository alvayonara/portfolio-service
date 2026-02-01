package com.alvayonara.portfolioservice.admin.dto;

public record PresignedAssetUploadResponse(
        String uploadUrl,
        String publicUrl
) {
}
