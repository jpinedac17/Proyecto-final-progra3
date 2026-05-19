package com.app.backend.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "nodes")
@Profile("mongo")
public class MongoNodeDocument {

    @Id
    private Long id;

    private String value;

    private Long parentId;

    protected MongoNodeDocument() {
    }

    public MongoNodeDocument(Long id, String value, Long parentId) {
        this.id = id;
        this.value = value;
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Long getParentId() {
        return parentId;
    }
}
