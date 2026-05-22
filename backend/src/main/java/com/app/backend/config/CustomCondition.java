package com.app.backend.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CustomCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String strategy = context.getEnvironment().getProperty("app.tree-strategy");
        return "custom".equalsIgnoreCase(strategy);
    }
}
