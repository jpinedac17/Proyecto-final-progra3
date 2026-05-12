package com.app.backend.config;

import com.app.backend.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public TreeRepository treeRepository() {
        return new MemoryRepository(); // luego cambiarás esto
    }
}
