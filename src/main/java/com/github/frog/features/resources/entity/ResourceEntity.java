package com.github.frog.features.resources.entity;

import com.github.frog.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "resources")
public class ResourceEntity extends BaseEntity {

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;
}
