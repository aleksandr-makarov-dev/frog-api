package com.github.frog.features.tasks.service;

import com.github.frog.features.tasks.dto.AttachmentResponse;

public interface AttachmentService {
    AttachmentResponse addAttachment(Long taskId, Long resourceId);
    void removeAttachment(Long taskId, Long attachmentId);
}
