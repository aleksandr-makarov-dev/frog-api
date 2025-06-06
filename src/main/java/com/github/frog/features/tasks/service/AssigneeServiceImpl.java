package com.github.frog.features.tasks.service;

import com.github.frog.features.tasks.dto.AssigneeResponse;
import com.github.frog.features.tasks.entity.AssigneeEntity;
import com.github.frog.features.tasks.entity.TaskEntity;
import com.github.frog.features.tasks.exception.AssigneeNotFoundException;
import com.github.frog.features.tasks.mapper.AssigneeMapper;
import com.github.frog.features.tasks.repository.AssigneeRepository;
import com.github.frog.features.users.entity.UserEntity;
import com.github.frog.features.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssigneeServiceImpl implements AssigneeService {

    private final AssigneeRepository assigneeRepository;

    private final TaskService taskService;
    private final UserService userService;

    private final AssigneeMapper assigneeMapper;

    @Override
    public AssigneeResponse addAssignee(Long taskId, Long userId) {
        TaskEntity task = taskService.getTaskEntityByIdOrThrow(taskId);
        UserEntity user = userService.getUserEntityByIdOrThrow(userId);

        AssigneeEntity assignee = assigneeMapper.toAssigneeEntity(task, user);

        return assigneeMapper.toAssigneeResponse(assigneeRepository.save(assignee));
    }

    @Override
    public void removeAssignee(Long taskId, Long userId) {
        AssigneeEntity assignee = assigneeRepository.findByTaskIdAndUserId(taskId, userId)
                .orElseThrow(() -> new AssigneeNotFoundException("User with ID=%d is not assigned to Task with ID=%d".formatted(userId, taskId)));
        assigneeRepository.delete(assignee);
    }
}
