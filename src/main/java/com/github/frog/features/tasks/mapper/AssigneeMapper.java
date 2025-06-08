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
        AssigneeEntity assignee = new AssigneeEntity();
        assignee.setAssignedAt(LocalDateTime.now());
        task.addAssignee(assignee);
        user.addAssignee(assignee);

        return assignee;
    }

    public AssigneeResponse toAssigneeResponse(AssigneeEntity entity) {
        return new AssigneeResponse(
                entity.getId(),
                entity.getAssignedAt());
    }
}
