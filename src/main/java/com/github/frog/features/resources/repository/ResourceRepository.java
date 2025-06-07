package com.github.frog.features.resources.repository;

import com.github.frog.features.resources.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {
}
