package com.arbol.tree_lib.operations;

import com.arbol.tree_lib.model.TreeNode;

public class TreeOperations {

    private TreeNode root;
    
    // 1. CREAR RAÍZ
    public TreeNode createRoot(Long id, String value) {
        if (root != null) {
            throw new RuntimeException("Root already exists");
        }

        root = new TreeNode(id, value);
        return root;
    }

    // 2. AGREGAR HIJO
    public TreeNode addChild(Long parentId, Long id, String value) {

        if (root == null) {
            throw new RuntimeException("Tree is empty");
        }

        TreeNode parent = findNode(root, parentId);

        if (parent == null) {
            throw new RuntimeException("Parent not found");
        }

        TreeNode child = new TreeNode(id, value);
        parent.addChild(child);

        return child;
    }

    // 3. OBTENER ÁRBOL COMPLETO
    public TreeNode getTree() {
        if (root == null) {
            throw new RuntimeException("Tree is empty");
        }

        return root;
    }

    // 4. SUBÁRBOL
    public TreeNode getSubTree(Long id) {
        return findNode(root, id);
    }

    // 5. RUTA DESDE RAÍZ DE UN NODO
    public TreeNode[] getPath(Long id) {
        TreeNode[] path = new TreeNode[100];
        int[] size = new int[1];

        if (findPath(root, id, path, size)) {
            return copy(path, size[0]);
        }

        return new TreeNode[0];
    }

    private boolean findPath(TreeNode node, Long id, TreeNode[] path, int[] size) {
        if (node == null) return false;

        path[size[0]++] = node;

        if (node.getId().equals(id)) return true;

        TreeNode[] children = node.getChildren();

        for (int i = 0; i < children.length; i++) {
            if (findPath(children[i], id, path, size)) return true;
        }

        size[0]--;
        return false;
    }

    // 6. Recorrido DFS
    public TreeNode[] dfs() {
        TreeNode[] result = new TreeNode[100];
        int[] size = new int[1];

        dfsHelper(root, result, size);

        return copy(result, size[0]);
    }

    private void dfsHelper(TreeNode node, TreeNode[] result, int[] size) {
        if (node == null) return;

        result[size[0]++] = node;

        TreeNode[] children = node.getChildren();

        for (int i = 0; i < children.length; i++) {
            dfsHelper(children[i], result, size);
        }
    }

    // 7. Recorrido BFS
    public TreeNode[] bfs() {
        if (root == null) return new TreeNode[0];

        TreeNode[] result = new TreeNode[100];
        TreeNode[] queue = new TreeNode[100];

        int front = 0, rear = 0, size = 0;

        queue[rear++] = root;

        while (front < rear) {
            TreeNode current = queue[front++];

            result[size++] = current;

            TreeNode[] children = current.getChildren();

            for (int i = 0; i < children.length; i++) {
                queue[rear++] = children[i];
            }
        }

        return copy(result, size);
    }

    // 8. ALTURA DEL ÁRBOL
    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(TreeNode node) {
        if (node == null) return 0;

        TreeNode[] children = node.getChildren();
        int max = 0;

        for (int i = 0; i < children.length; i++) {
            int h = heightHelper(children[i]);
            if (h > max) max = h;
        }

        return max + 1;
    }

    // 9. PROFUNDIDAD DE UN NODO
    public int depth(Long id) {
        TreeNode[] path = getPath(id);
        if (path.length == 0) return -1;
        return path.length - 1;
    }

    // 10. ANCESTROS DE UN NODO
    public TreeNode[] ancestors(Long id) {
        TreeNode[] path = getPath(id);

        if (path.length <= 1) return new TreeNode[0];

        TreeNode[] result = new TreeNode[path.length - 1];

        for (int i = 0; i < path.length - 1; i++) {
            result[i] = path[i];
        }

        return result;
    }

    // 11. VALIDAR CICLOS
    public boolean validate() {
        return hasCycle(root, new TreeNode[100], new int[1]);
    }

    private boolean hasCycle(TreeNode node, TreeNode[] visited, int[] size) {
        if (node == null) return false;

        for (int i = 0; i < size[0]; i++) {
            if (visited[i] == node) return true;
        }

        visited[size[0]++] = node;

        TreeNode[] children = node.getChildren();

        for (int i = 0; i < children.length; i++) {
            if (hasCycle(children[i], visited, size)) return true;
        }

        size[0]--;
        return false;
    }

    // AUX: BUSCAR NODO
    private TreeNode findNode(TreeNode node, Long id) {
        if (node == null) return null;

        if (node.getId().equals(id)) return node;

        TreeNode[] children = node.getChildren();

        for (int i = 0; i < children.length; i++) {
            TreeNode found = findNode(children[i], id);
            if (found != null) return found;
        }

        return null;
    }

    // 	AUX: COPIAR ARRAY
    private TreeNode[] copy(TreeNode[] original, int size) {
        TreeNode[] result = new TreeNode[size];
        for (int i = 0; i < size; i++) {
            result[i] = original[i];
        }
        return result;
    }
}