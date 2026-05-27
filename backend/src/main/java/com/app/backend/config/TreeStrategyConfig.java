package com.app.backend.config;

import com.arbol.tree_lib.strategy.CollectionsTreeStrategy;
import com.arbol.tree_lib.strategy.CustomTreeStrategy;
import com.arbol.tree_lib.strategy.TreeAlgorithmStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TreeStrategyConfig {

    // Estrategia Collections — activa cuando app.tree-strategy=collections 
    @Bean
    @Conditional(CollectionsCondition.class)
    public TreeAlgorithmStrategy collectionsTreeStrategy() {
        return new CollectionsTreeStrategy();
    }

 // Estrategia Custom — activa cuando app.tree-strategy=custom 
    @Bean
    @Conditional(CustomCondition.class)
    public TreeAlgorithmStrategy customTreeStrategy() {
        return new CustomTreeStrategy();
    }
}
