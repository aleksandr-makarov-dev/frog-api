package com.github.frog.features.tasks.service;

import com.github.frog.features.tasks.dto.TaskCreateRequest;
import com.github.frog.features.tasks.dto.TaskDetailsResponse;
import com.github.frog.features.tasks.dto.TaskSummaryResponse;
import com.github.frog.features.tasks.dto.TaskUpdateRequest;
import com.github.frog.features.tasks.entity.TaskEntity;
import com.github.frog.features.tasks.exception.TaskNotFoundException;
import com.github.frog.features.tasks.mapper.TaskMapper;
import com.github.frog.features.tasks.repository.TaskRepository;
import com.github.frog.features.users.entity.UserEntity;
import com.github.frog.features.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final UserService userService;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    @Override
    public TaskDetailsResponse createTask(TaskCreateRequest request) {

        UserEntity user = userService.getUserEntityByIdOrThrow(request.createdById());

        TaskEntity taskEntity = taskMapper.toTaskEntity(request);
        user.addTask(taskEntity);

        return taskMapper.toDetailsResponse(taskRepository.save(taskEntity));
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskSummaryResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toSummaryResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public TaskDetailsResponse getTaskById(Long id) {
        return taskMapper.toDetailsResponse(getTaskEntityByIdOrThrow(id));
    }

    @Transactional
    @Override
    public TaskDetailsResponse updateTaskById(Long id, TaskUpdateRequest request) {
        TaskEntity task = getTaskEntityByIdOrThrow(id);
        TaskEntity updatedTask = taskMapper.updateEntity(task, request);

        return taskMapper.toDetailsResponse(taskRepository.save(updatedTask));
    }

    @Transactional
    @Override
    public void deleteTaskById(Long id) {
        TaskEntity task = getTaskEntityByIdOrThrow(id);
        taskRepository.delete(task);
    }

    public TaskEntity getTaskEntityByIdOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID=%s not found".formatted(id)));
    }
}
