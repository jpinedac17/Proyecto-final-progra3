package com.arbol.tree_lib.model;

public class TreeNode {

    private Long id;
    private String value;

    private TreeNode[] children;
    private int size;

    public TreeNode(Long id, String value) {
        this.id = id;
        this.value = value;
        this.children = new TreeNode[2]; // tamaño inicial
        this.size = 0;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public TreeNode[] getChildren() {
        TreeNode[] result = new TreeNode[size];
        for (int i = 0; i < size; i++) {
            result[i] = children[i];
        }
        return result;
    }

    public void addChild(TreeNode child) {
        if (size == children.length) {
            resize();
        }

        children[size] = child;
        size++;
    }

    private void resize() {
        TreeNode[] newChildren = new TreeNode[children.length * 2];

        for (int i = 0; i < children.length; i++) {
            newChildren[i] = children[i];
        }

        children = newChildren;
    }
}