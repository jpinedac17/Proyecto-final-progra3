package com.arbol.tree_lib.operations;

import com.arbol.tree_lib.model.TreeNode;

public class TreeOperations {

    // 4. SUBÁRBOL
    public TreeNode getSubTree(TreeNode root, Long id) {
        return findNode(root, id);
    }

    // 5. RUTA
    public TreeNode[] getPath(TreeNode root, Long id) {
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

    // 6. DFS
    public TreeNode[] dfs(TreeNode root) {
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

    // 7. BFS
    public TreeNode[] bfs(TreeNode root) {
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

    // 8. ALTURA
    public int height(TreeNode root) {
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

    // 9. PROFUNDIDAD
    public int depth(TreeNode root, Long id) {
        TreeNode[] path = getPath(root, id);
        if (path.length == 0) return -1;
        return path.length - 1;
    }

    // 10. ANCESTROS
    public TreeNode[] getAncestors(TreeNode root, Long id) {
        TreeNode[] path = getPath(root, id);

        if (path.length <= 1) return new TreeNode[0];

        TreeNode[] result = new TreeNode[path.length - 1];

        for (int i = 0; i < path.length - 1; i++) {
            result[i] = path[i];
        }

        return result;
    }

    // 11. VALIDAR
    public boolean validate(TreeNode root) {
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

    // AUX
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

    private TreeNode[] copy(TreeNode[] original, int size) {
        TreeNode[] result = new TreeNode[size];
        for (int i = 0; i < size; i++) {
            result[i] = original[i];
        }
        return result;
    }
}