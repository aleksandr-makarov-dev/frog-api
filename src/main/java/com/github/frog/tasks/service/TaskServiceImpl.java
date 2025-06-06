package com.github.frog.tasks.service;

import com.github.frog.tasks.dto.TaskCreateRequest;
import com.github.frog.tasks.dto.TaskDetailsResponse;
import com.github.frog.tasks.dto.TaskSummaryResponse;
import com.github.frog.tasks.dto.TaskUpdateRequest;
import com.github.frog.tasks.entity.TaskEntity;
import com.github.frog.tasks.exception.TaskNotFoundException;
import com.github.frog.tasks.mapper.TaskMapper;
import com.github.frog.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    @Override
    public TaskDetailsResponse createTask(TaskCreateRequest request) {
        TaskEntity taskEntity = taskMapper.toEntity(request);
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
        return taskMapper.toDetailsResponse(getTaskByIdOrThrow(id));
    }

    @Transactional
    @Override
    public TaskDetailsResponse updateTaskById(Long id, TaskUpdateRequest request) {
        TaskEntity task = getTaskByIdOrThrow(id);
        TaskEntity updatedTask = taskMapper.updateEntity(task, request);

        return taskMapper.toDetailsResponse(taskRepository.save(updatedTask));
    }

    @Transactional
    @Override
    public void deleteTaskById(Long id) {
        TaskEntity task = getTaskByIdOrThrow(id);
        taskRepository.delete(task);
    }

    private TaskEntity getTaskByIdOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID=%s does not exist".formatted(id)));
    }
}
