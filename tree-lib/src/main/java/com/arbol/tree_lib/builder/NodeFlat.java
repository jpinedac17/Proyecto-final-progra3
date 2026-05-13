package com.arbol.tree_lib.builder;

public class NodeFlat {

    private Long id;
    private String value;
    private Long parentId;

    public NodeFlat(Long id, String value, Long parentId) {
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