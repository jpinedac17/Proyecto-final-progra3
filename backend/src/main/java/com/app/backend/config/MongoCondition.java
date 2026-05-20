package com.app.backend.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MongoCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String repoType = context.getEnvironment().getProperty("app.storage");
        return "mongo".equalsIgnoreCase(repoType);
    }
}
