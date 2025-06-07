package com.github.frog.features.resources.service;

import com.github.frog.features.resources.dto.ResourceResponse;
import com.github.frog.features.resources.entity.ResourceEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {

    ResourceResponse createResource(MultipartFile file);

    ResourceResponse getResourceById(Long id);

    ResourceEntity getResourceEntityByIdOrThrow(Long id);
}
