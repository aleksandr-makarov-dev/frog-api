package com.github.frog.features.tasks.mapper;

import com.github.frog.features.resources.entity.ResourceEntity;
import com.github.frog.features.tasks.dto.AttachmentResponse;
import com.github.frog.features.tasks.entity.AttachmentEntity;
import com.github.frog.features.tasks.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AttachmentMapper {

    public AttachmentEntity toAttachmentEntity(TaskEntity task, ResourceEntity resource) {
        AttachmentEntity attachment = new AttachmentEntity();
        attachment.setAttachedAt(LocalDateTime.now());

        task.addAttachment(attachment);
        resource.addAttachment(attachment);

        return attachment;
    }

    public AttachmentResponse  toAttachmentResponse(AttachmentEntity entity) {
        return new  AttachmentResponse(
                entity.getId(),
                entity.getAttachedAt()
        );
    }
}
