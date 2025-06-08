package com.github.frog.features.tasks.service;

import com.github.frog.features.resources.entity.ResourceEntity;
import com.github.frog.features.resources.service.ResourceService;
import com.github.frog.features.tasks.dto.AttachmentResponse;
import com.github.frog.features.tasks.entity.AttachmentEntity;
import com.github.frog.features.tasks.entity.TaskEntity;
import com.github.frog.features.tasks.exception.AttachmentNotFoundException;
import com.github.frog.features.tasks.mapper.AttachmentMapper;
import com.github.frog.features.tasks.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final TaskService taskService;
    private final ResourceService resourceService;
    private final AttachmentRepository attachmentRepository;

    private final AttachmentMapper attachmentMapper;

    @Transactional
    @Override
    public AttachmentResponse addAttachment(Long taskId, Long resourceId) {
        TaskEntity task = taskService.getTaskEntityByIdOrThrow(taskId);
        ResourceEntity resource = resourceService.getResourceEntityByIdOrThrow(resourceId);

        AttachmentEntity attachment = attachmentMapper.toAttachmentEntity(task, resource);

        return attachmentMapper.toAttachmentResponse(attachmentRepository.save(attachment));
    }

    @Transactional
    @Override
    public void removeAttachment(Long taskId, Long attachmentId) {
        AttachmentEntity attachment = attachmentRepository.findWithTaskById(attachmentId)
                .orElseThrow(() -> new AttachmentNotFoundException("Attachment with ID=%d not found".formatted(attachmentId)));

        if (!attachment.getTask().getId().equals(taskId)) {
            throw new AttachmentNotFoundException("Attachment with ID=%d does not belong to task with ID=%d".formatted(attachmentId, taskId));
        }

        attachmentRepository.delete(attachment);
    }
}
