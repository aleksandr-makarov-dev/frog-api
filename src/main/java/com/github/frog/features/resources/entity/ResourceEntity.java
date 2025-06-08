package com.github.frog.features.resources.entity;

import com.github.frog.entity.BaseEntity;
import com.github.frog.features.tasks.entity.AttachmentEntity;
import com.github.frog.features.tasks.entity.TaskEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "resource",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @Builder.Default
    private List<AttachmentEntity> attachments = new ArrayList<>();

    public void addAttachment(AttachmentEntity attachment) {
        attachments.add(attachment);
        attachment.setResource(this);
    }
}
