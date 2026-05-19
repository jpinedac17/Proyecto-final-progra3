package com.arbol.tree_lib.builder;

import com.arbol.tree_lib.model.TreeNode;
import java.util.List;

public class TreeBuilder {

    public static TreeNode build(List<NodeFlat> nodes) {

        // Convertir List → Array (permitido solo como entrada)
        NodeFlat[] nodeArray = nodes.toArray(new NodeFlat[0]);
        TreeNode[] created = new TreeNode[nodeArray.length];

        TreeNode root = null;

        // Validar IDs duplicados
        for (int i = 0; i < nodeArray.length; i++) {
            for (int j = i + 1; j < nodeArray.length; j++) {
                if (nodeArray[i].getId().equals(nodeArray[j].getId())) {
                    throw new RuntimeException("Ya existe un nodo con el ID:  " + nodeArray[i].getId());
                }
            }
        }

        // Validar cantidad de raíces
        int rootCount = 0;
        for (int i = 0; i < nodeArray.length; i++) {
            if (nodeArray[i].getParentId() == null) {
                rootCount++;
            }
        }

        if (rootCount != 1) {
            throw new RuntimeException("El árbol debe tener una única raíz");
        }

        // Crear nodos
        for (int i = 0; i < nodeArray.length; i++) {
            created[i] = new TreeNode(
                    nodeArray[i].getId(),
                    nodeArray[i].getValue()
            );
        }

        // Conectar relaciones
        for (int i = 0; i < nodeArray.length; i++) {

            TreeNode current = created[i];

            if (nodeArray[i].getParentId() == null) {
                root = current;
            } else {

                TreeNode parent = findNode(created, nodeArray[i].getParentId());

                if (parent == null) {
                    throw new RuntimeException("No se encontró el nodo padre con id: " + nodeArray[i].getParentId());
                }

                parent.addChild(current);
            }
        }

        // Validar que exista raíz
        if (root == null) {
            throw new RuntimeException("El árbol debe tener una raíz");
        }

        return root;
    }

    // Búsqueda manual dentro del array
    private static TreeNode findNode(TreeNode[] nodes, Long id) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].getId().equals(id)) {
                return nodes[i];
            }
        }
        return null;
    }
}