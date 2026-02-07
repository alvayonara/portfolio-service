package com.alvayonara.portfolioservice.admin.dto;

public record UploadResponseDetail(
        String uploadUrl,
        String publicUrl,
        String s3Key
) {
}
