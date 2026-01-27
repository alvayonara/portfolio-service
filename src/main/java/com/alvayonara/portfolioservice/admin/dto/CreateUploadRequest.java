package com.alvayonara.portfolioservice.admin.dto;

public record CreateUploadRequest(
        String filename,
        String contentType,
        long size
) {
}
