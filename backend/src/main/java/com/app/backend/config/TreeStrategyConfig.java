package com.app.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.arbol.tree_lib.strategy.CollectionsTreeStrategy;
import com.arbol.tree_lib.strategy.TreeAlgorithmStrategy;

@Configuration
public class TreeStrategyConfig {

    @Bean
    public TreeAlgorithmStrategy treeAlgorithmStrategy() {

        return new CollectionsTreeStrategy();
    }
}