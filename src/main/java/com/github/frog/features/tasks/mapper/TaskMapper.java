package com.github.frog.features.tasks.mapper;

import com.github.frog.features.tasks.dto.TaskCreateRequest;
import com.github.frog.features.tasks.dto.TaskDetailsResponse;
import com.github.frog.features.tasks.dto.TaskSummaryResponse;
import com.github.frog.features.tasks.dto.TaskUpdateRequest;
import com.github.frog.features.tasks.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskMapper {

    public TaskEntity toTaskEntity(TaskCreateRequest request) {
        return TaskEntity.builder()
                .name(request.name())
                .description(request.description())
                .priority(request.priority())
                .dueDate(request.dueDate())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public TaskDetailsResponse toDetailsResponse(TaskEntity entity) {
        return new TaskDetailsResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPriority(),
                entity.getCreatedAt(),
                entity.getDueDate()
        );
    }

    public TaskSummaryResponse toSummaryResponse(TaskEntity entity) {
        return new TaskSummaryResponse(
                entity.getId(),
                entity.getName(),
                entity.getPriority(),
                entity.getDueDate()
        );
    }

    public TaskEntity updateEntity(TaskEntity entity, TaskUpdateRequest request) {
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setPriority(request.priority());
        entity.setDueDate(request.dueDate());

        return entity;
    }
}
