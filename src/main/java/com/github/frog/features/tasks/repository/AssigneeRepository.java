package com.github.frog.features.tasks.repository;

import com.github.frog.features.tasks.entity.AssigneeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssigneeRepository extends JpaRepository<AssigneeEntity, Long> {
    Optional<AssigneeEntity> findByTaskIdAndUserId(Long taskId, Long userId);
}
