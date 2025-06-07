package com.github.frog.features.resources.mapper;

import com.github.frog.features.resources.dto.ResourceResponse;
import com.github.frog.features.resources.entity.ResourceEntity;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class ResourceMapper {

    public ResourceResponse toResourceResponse(ResourceEntity entity, String downloadUrl) {
        return new ResourceResponse(
                entity.getId(),
                entity.getOriginalName(),
                entity.getExtension(),
                entity.getSize(),
                entity.getMimeType(),
                downloadUrl
        );
    }

    public ResourceEntity toResourceEntity(MultipartFile file) {
        String originalName = FilenameUtils.getBaseName(file.getOriginalFilename());
        Long size = file.getSize();
        String mimeType = file.getContentType();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String safeName = UUID.randomUUID().toString();
        String key = safeName + "." + extension;

        return ResourceEntity.builder()
                .originalName(originalName)
                .size(size)
                .mimeType(mimeType)
                .key(key)
                .extension(extension)
                .build();
    }

}
