package com.arbol.tree_lib.builder;

import com.arbol.tree_lib.model.TreeNode;

import java.util.List;

public class TreeBuilder {

    public static TreeNode build(List<NodeFlat> nodes) {

        // 🔹 Convertir List → Array (permitido solo como entrada)
        NodeFlat[] nodeArray = nodes.toArray(new NodeFlat[0]);
        TreeNode[] created = new TreeNode[nodeArray.length];

        TreeNode root = null;

        // 🔹 Validar cantidad de raíces
        int rootCount = 0;
        for (int i = 0; i < nodeArray.length; i++) {
            if (nodeArray[i].getParentId() == null) {
                rootCount++;
            }
        }

        if (rootCount != 1) {
            throw new RuntimeException("Tree must have exactly one root");
        }

        // 🔹 Crear nodos
        for (int i = 0; i < nodeArray.length; i++) {
            created[i] = new TreeNode(
                    nodeArray[i].getId(),
                    nodeArray[i].getValue()
            );
        }

        // 🔹 Conectar relaciones
        for (int i = 0; i < nodeArray.length; i++) {

            TreeNode current = findNode(created, nodeArray[i].getId());

            if (nodeArray[i].getParentId() == null) {
                root = current;
            } else {

                TreeNode parent = findNode(created, nodeArray[i].getParentId());

                if (parent == null) {
                    throw new RuntimeException("Parent not found: " + nodeArray[i].getParentId());
                }

                parent.addChild(current);
            }
        }

        if (root == null) {
            throw new RuntimeException("Tree must have a root");
        }

        return root;
    }

    // 🔍 Búsqueda manual
    private static TreeNode findNode(TreeNode[] nodes, Long id) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].getId().equals(id)) {
                return nodes[i];
            }
        }
        return null;
    }
}