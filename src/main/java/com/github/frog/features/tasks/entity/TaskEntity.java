package com.github.frog.features.tasks.entity;

import com.github.frog.entity.BaseEntity;
import com.github.frog.features.users.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tasks")
public class TaskEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<AssigneeEntity> assignees = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    UserEntity createdBy;

    @OneToMany(mappedBy = "task",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<AttachmentEntity> attachments = new ArrayList<>();

    public void addAssignee(AssigneeEntity entity) {
        assignees.add(entity);
        entity.setTask(this);
    }

    public void addAttachment(AttachmentEntity entity) {
        attachments.add(entity);
        entity.setTask(this);
    }
}
