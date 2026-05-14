package com.app.backend.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NodeJpaRepository extends JpaRepository<NodeEntity, Long> {

    Optional<NodeEntity> findByParentIdIsNull();
}