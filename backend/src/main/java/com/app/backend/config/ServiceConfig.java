package com.app.backend.config;

import com.app.backend.repository.TreeRepository;
import com.app.backend.service.TreeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public TreeService treeService(TreeRepository repo) {
        return new TreeService(repo);
    }
}
