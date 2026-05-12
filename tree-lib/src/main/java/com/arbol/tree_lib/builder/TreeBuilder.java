package com.arbol.tree_lib.builder;

import com.arbol.tree_lib.model.TreeNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeBuilder {

    public static TreeNode build(List<NodeFlat> nodes) {

        Map<Long, TreeNode> map = new HashMap<>();
        TreeNode root = null;

        // 🔹 Validar cantidad de raíces
        int rootCount = 0;
        for (NodeFlat n : nodes) {
            if (n.getParentId() == null) {
                rootCount++;
            }
        }

        if (rootCount != 1) {
            throw new RuntimeException("Tree must have exactly one root");
        }

        // 🔹 Crear nodos
        for (NodeFlat n : nodes) {
            map.put(n.getId(), new TreeNode(n.getId(), n.getValue()));
        }

        // 🔹 Conectar relaciones
        for (NodeFlat n : nodes) {

            if (n.getParentId() == null) {
                root = map.get(n.getId());
            } else {
                TreeNode parent = map.get(n.getParentId());

                if (parent == null) {
                    throw new RuntimeException("Parent not found: " + n.getParentId());
                }

                parent.addChild(map.get(n.getId()));
            }
        }

        // 🔹 Validar que sí haya raíz
        if (root == null) {
            throw new RuntimeException("Tree must have a root");
        }

        return root;
    }
}
