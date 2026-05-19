package com.app.backend.repository.postgres;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("postgres")
public interface NodeJpaRepository extends JpaRepository<NodeEntity, Long> {

    Optional<NodeEntity> findByParentIdIsNull();
}