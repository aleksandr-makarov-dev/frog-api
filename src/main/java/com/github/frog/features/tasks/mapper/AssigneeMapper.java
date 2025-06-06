package com.github.frog.features.tasks.mapper;

import com.github.frog.features.tasks.dto.AssigneeResponse;
import com.github.frog.features.tasks.entity.AssigneeEntity;
import com.github.frog.features.tasks.entity.TaskEntity;
import com.github.frog.features.users.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AssigneeMapper {

    public AssigneeEntity toAssigneeEntity(TaskEntity task, UserEntity user) {
        return AssigneeEntity.builder()
                .task(task)
                .user(user)
                .assignedAt(LocalDateTime.now())
                .build();
    }

    public AssigneeResponse toAssigneeResponse(AssigneeEntity entity) {
        return new AssigneeResponse(
                entity.getTask().getId(),
                entity.getUser().getId(),
                entity.getAssignedAt());
    }
}
