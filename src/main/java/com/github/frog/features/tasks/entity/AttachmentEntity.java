package com.github.frog.features.tasks.entity;

import com.github.frog.entity.BaseEntity;
import com.github.frog.features.resources.entity.ResourceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "attachments", uniqueConstraints = {@UniqueConstraint(name = "uk_task_resource", columnNames = {"task_id", "resource_id"})})
public class AttachmentEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private TaskEntity task;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resource_id", nullable = false)
    private ResourceEntity resource;

    @Column(name = "attached_at", nullable = false, updatable = false)
    private LocalDateTime attachedAt;

}
