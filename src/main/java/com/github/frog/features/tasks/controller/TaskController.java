package com.github.frog.features.tasks.controller;

import com.github.frog.features.tasks.dto.*;
import com.github.frog.features.tasks.service.AssigneeService;
import com.github.frog.features.tasks.service.AttachmentService;
import com.github.frog.features.tasks.service.TaskService;
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
    private final AssigneeService assigneeService;
    private final AttachmentService attachmentService;

    @PostMapping
    public ResponseEntity<TaskDetailsResponse> createTask(@RequestBody @Valid TaskCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @GetMapping
    public ResponseEntity<List<TaskSummaryResponse>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks());
    }

    @GetMapping("{taskId}")
    public ResponseEntity<TaskDetailsResponse> getTask(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskById(taskId));
    }

    @PutMapping("{taskId}")
    public ResponseEntity<TaskDetailsResponse> updateTask(@PathVariable("taskId") Long taskId, @RequestBody @Valid TaskUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTaskById(taskId, request));
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity<TaskDetailsResponse> deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTaskById(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("{taskId}/assignees")
    public ResponseEntity<AssigneeResponse> addAssignee(@PathVariable("taskId") Long taskId, @RequestBody @Valid AssigneeAddRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assigneeService.addAssignee(taskId, request.userId()));
    }

    @DeleteMapping("{taskId}/assignees/{assigneeId}")
    public ResponseEntity<?> removeAssignee(@PathVariable("taskId") Long taskId, @PathVariable("assigneeId") Long assigneeId) {
        assigneeService.removeAssignee(taskId, assigneeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("{taskId}/attachments")
    public ResponseEntity<AttachmentResponse> addAttachment(@PathVariable("taskId") Long taskId, @RequestBody @Valid AttachmentAddRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attachmentService.addAttachment(taskId, request.resourceId()));
    }

    @DeleteMapping("{taskId}/attachments/{attachmentId}")
    public ResponseEntity<?> removeAttachment(@PathVariable("taskId") Long taskId, @PathVariable("attachmentId") Long attachmentId) {
        attachmentService.removeAttachment(taskId, attachmentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
