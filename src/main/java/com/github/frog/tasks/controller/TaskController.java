package com.github.frog.tasks.controller;

import com.github.frog.tasks.dto.TaskCreateRequest;
import com.github.frog.tasks.dto.TaskDetailsResponse;
import com.github.frog.tasks.dto.TaskSummaryResponse;
import com.github.frog.tasks.dto.TaskUpdateRequest;
import com.github.frog.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDetailsResponse> createTask(@RequestBody @Valid TaskCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @GetMapping
    public ResponseEntity<List<TaskSummaryResponse>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks());
    }

    @GetMapping("{id}")
    public ResponseEntity<TaskDetailsResponse> getTask(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<TaskDetailsResponse> updateTask(@PathVariable Long id, @RequestBody @Valid TaskUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTaskById(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TaskDetailsResponse> deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
