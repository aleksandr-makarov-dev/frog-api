package com.github.frog.features.tasks.repository;

import com.github.frog.features.tasks.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {

    Optional<AttachmentEntity> findWithTaskById(Long id);
}
