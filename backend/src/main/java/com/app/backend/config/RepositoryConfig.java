package com.app.backend.config;

import com.app.backend.repository.MemoryRepository;
import com.app.backend.repository.TreeRepository;
import com.app.backend.repository.postgres.NodeJpaRepository;
import com.app.backend.repository.postgres.PostgresRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public TreeRepository treeRepository(
            @Value("${app.storage:memory}") String storage,
            NodeJpaRepository nodeJpaRepository
    ) {
        if ("postgres".equalsIgnoreCase(storage)) {
            return new PostgresRepository(nodeJpaRepository);
        }

        return new MemoryRepository();
    }
}