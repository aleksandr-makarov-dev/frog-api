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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AssigneeServiceImpl implements AssigneeService {

    private final AssigneeRepository assigneeRepository;

    private final TaskService taskService;
    private final UserService userService;

    private final AssigneeMapper assigneeMapper;

    @Transactional
    @Override
    public AssigneeResponse addAssignee(Long taskId, Long userId) {
        TaskEntity task = taskService.getTaskEntityByIdOrThrow(taskId);
        UserEntity user = userService.getUserEntityByIdOrThrow(userId);

        AssigneeEntity assignee = assigneeMapper.toAssigneeEntity(task, user);

        return assigneeMapper.toAssigneeResponse(assigneeRepository.save(assignee));
    }

    @Transactional
    @Override
    public void removeAssignee(Long taskId, Long assigneeId) {
        AssigneeEntity assignee = assigneeRepository.findWithTaskById(assigneeId)
                .orElseThrow(() -> new AssigneeNotFoundException("Assignee with ID=%d not found".formatted(assigneeId)));

        if (!assignee.getTask().getId().equals(taskId)) {
            throw new AssigneeNotFoundException("Assignment with ID=%d doesn't belong to task with ID=%d".formatted(assigneeId, taskId));
        }

        assigneeRepository.delete(assignee);
    }
}
