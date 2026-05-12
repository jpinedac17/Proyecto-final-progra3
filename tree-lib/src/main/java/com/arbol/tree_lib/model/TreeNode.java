package com.arbol.tree_lib.model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private Long id;
    private String value;
    private List<TreeNode> children;

    public TreeNode(Long id, String value) {
        this.id = id;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void addChild(TreeNode child) {
        this.children.add(child);
    }
}