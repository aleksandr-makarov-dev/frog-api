package com.github.frog.features.resources.repository;

import org.springframework.web.multipart.MultipartFile;

public interface S3StorageRepository {

    void putObject(String key, MultipartFile file);

    String getPresignedUrl(String key);
}
