package com.arbol.tree_lib.model;

public class TreeNode {

    private Long id;
    private String value;

    private TreeNode[] children;
    private int size;

    // Constructor
    public TreeNode(Long id, String value) {
        if (id == null) {
            throw new RuntimeException("El ID no puede ser null");
        }

        if (value == null) {
            throw new RuntimeException("El value no puede ser null");
        }

        this.id = id;
        this.value = value;
        this.children = new TreeNode[2]; // tamaño inicial
        this.size = 0;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    // Retorna SOLO los hijos válidos (sin nulls)
    public TreeNode[] getChildren() {
        TreeNode[] result = new TreeNode[size];

        for (int i = 0; i < size; i++) {
            result[i] = children[i];
        }

        return result;
    }

    // Agregar hijo
    public void addChild(TreeNode child) {

        if (child == null) {
            throw new RuntimeException("Child cannot be null");
        }

        // evitar duplicados
        for (int i = 0; i < size; i++) {
            if (children[i].getId().equals(child.getId())) {
                throw new RuntimeException("Duplicate child id: " + child.getId());
            }
        }

        if (size == children.length) {
            resize();
        }

        children[size] = child;
        size++;
    }

    // Redimensionar array manualmente
    private void resize() {
        TreeNode[] newChildren = new TreeNode[children.length * 2];

        for (int i = 0; i < children.length; i++) {
            newChildren[i] = children[i];
        }

        children = newChildren;
    }

    // Debug útil
    @Override
    public String toString() {
        return "TreeNode{id=" + id + ", value='" + value + "'}";
    }
}