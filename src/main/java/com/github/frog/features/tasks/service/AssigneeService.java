package com.github.frog.features.tasks.service;

import com.github.frog.features.tasks.dto.AssigneeResponse;

public interface AssigneeService {

    AssigneeResponse addAssignee(Long taskId, Long userId);

    void removeAssignee(Long taskId, Long userId);
}
