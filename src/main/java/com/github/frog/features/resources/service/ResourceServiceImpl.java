package com.github.frog.features.resources.service;

import com.github.frog.features.resources.dto.ResourceResponse;
import com.github.frog.features.resources.entity.ResourceEntity;
import com.github.frog.features.resources.exception.ResourceNotFound;
import com.github.frog.features.resources.mapper.ResourceMapper;
import com.github.frog.features.resources.repository.ResourceRepository;
import com.github.frog.features.resources.repository.S3StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    private final S3StorageRepository s3StorageRepository;


    @Override
    public ResourceResponse createResource(MultipartFile file) {

        ResourceEntity resource = resourceMapper.toResourceEntity(file);

        s3StorageRepository.putObject(resource.getKey(), file);
        String downloadUrl = s3StorageRepository.getPresignedUrl(resource.getKey());

        return resourceMapper.toResourceResponse(
                resourceRepository.save(resource), downloadUrl);
    }

    @Override
    public ResourceResponse getResourceById(Long id) {
        ResourceEntity resource = getResourceEntityByIdOrThrow(id);

        String downloadUrl = s3StorageRepository.getPresignedUrl(resource.getKey());

        return resourceMapper.toResourceResponse(resource, downloadUrl);
    }

    @Override
    public ResourceEntity getResourceEntityByIdOrThrow(Long id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Resource with ID=%d not found".formatted(id)));
    }
}
