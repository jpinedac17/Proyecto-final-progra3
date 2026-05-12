package com.app.backend.model;

public class Node {

    private Long id;
    private String value;
    private Long parentId;

    public Node(Long id, String value, Long parentId) {
        this.id = id;
        this.value = value;
        this.parentId = parentId;
    }

    public Long getId() { return id; }
    public String getValue() { return value; }
    public Long getParentId() { return parentId; }
}
