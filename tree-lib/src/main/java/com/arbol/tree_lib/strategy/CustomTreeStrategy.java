package com.arbol.tree_lib.strategy;

import com.arbol.tree_lib.model.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomTreeStrategy implements TreeAlgorithmStrategy {

    @Override
    public String strategyName() {
        return "custom";
    }

    @Override
    public Optional<TreeNode> findSubTree(TreeNode root, Long nodeId) {
        TreeNode found = findNode(root, nodeId);

        if (found == null) {
            return Optional.empty();
        }

        return Optional.of(found);
    }

    @Override
    public List<TreeNode> pathFromRoot(TreeNode root, Long nodeId) {
        TreeNode[] path = new TreeNode[100];
        int[] size = new int[1];

        boolean found = findPath(root, nodeId, path, size);

        if (!found) {
            return toList(new TreeNode[0]);
        }

        return toList(copy(path, size[0]));
    }

    @Override
    public List<TreeNode> dfs(TreeNode root) {
        TreeNode[] result = new TreeNode[100];
        int[] size = new int[1];

        dfsRecursive(root, result, size);

        return toList(copy(result, size[0]));
    }

    @Override
    public List<TreeNode> bfs(TreeNode root) {
        if (root == null) {
            return toList(new TreeNode[0]);
        }

        TreeNode[] result = new TreeNode[100];
        TreeNode[] queue = new TreeNode[100];

        int front = 0;
        int rear = 0;
        int size = 0;

        queue[rear++] = root;

        while (front < rear) {
            TreeNode current = queue[front++];
            result[size++] = current;

            TreeNode[] children = current.getChildren();

            for (int i = 0; i < children.length; i++) {
                if (rear == queue.length) {
                    queue = resize(queue);
                }

                queue[rear++] = children[i];
            }

            if (size == result.length) {
                result = resize(result);
            }
        }

        return toList(copy(result, size));
    }

    @Override
    public int height(TreeNode root) {
        if (root == null) {
            return 0;
        }

        TreeNode[] children = root.getChildren();

        if (children.length == 0) {
            return 0;
        }

        int max = 0;

        for (int i = 0; i < children.length; i++) {
            int childHeight = height(children[i]);

            if (childHeight > max) {
                max = childHeight;
            }
        }

        return max + 1;
    }

    @Override
    public int depth(TreeNode root, Long nodeId) {
        return depthRecursive(root, nodeId, 0);
    }

    @Override
    public List<TreeNode> ancestors(TreeNode root, Long nodeId) {
        TreeNode[] path = new TreeNode[100];
        int[] size = new int[1];

        boolean found = findPath(root, nodeId, path, size);

        if (!found || size[0] <= 1) {
            return toList(new TreeNode[0]);
        }

        TreeNode[] ancestors = new TreeNode[size[0] - 1];

        for (int i = 0; i < size[0] - 1; i++) {
            ancestors[i] = path[i];
        }

        return toList(ancestors);
    }

    @Override
    public boolean validateNoCycles(TreeNode root) {
        return !hasCycle(root, new TreeNode[100], new int[1]);
    }

    private TreeNode findNode(TreeNode current, Long nodeId) {
        if (current == null || nodeId == null) {
            return null;
        }

        if (current.getId().equals(nodeId)) {
            return current;
        }

        TreeNode[] children = current.getChildren();

        for (int i = 0; i < children.length; i++) {
            TreeNode found = findNode(children[i], nodeId);

            if (found != null) {
                return found;
            }
        }

        return null;
    }

    private boolean findPath(TreeNode current, Long nodeId, TreeNode[] path, int[] size) {
        if (current == null || nodeId == null) {
            return false;
        }

        if (size[0] == path.length) {
            throw new RuntimeException("La ruta excede el tamaño máximo permitido");
        }

        path[size[0]] = current;
        size[0]++;

        if (current.getId().equals(nodeId)) {
            return true;
        }

        TreeNode[] children = current.getChildren();

        for (int i = 0; i < children.length; i++) {
            if (findPath(children[i], nodeId, path, size)) {
                return true;
            }
        }

        size[0]--;
        return false;
    }

    private void dfsRecursive(TreeNode current, TreeNode[] result, int[] size) {
        if (current == null) {
            return;
        }

        if (size[0] == result.length) {
            throw new RuntimeException("El recorrido DFS excede el tamaño máximo permitido");
        }

        result[size[0]] = current;
        size[0]++;

        TreeNode[] children = current.getChildren();

        for (int i = 0; i < children.length; i++) {
            dfsRecursive(children[i], result, size);
        }
    }

    private int depthRecursive(TreeNode current, Long nodeId, int currentDepth) {
        if (current == null || nodeId == null) {
            return -1;
        }

        if (current.getId().equals(nodeId)) {
            return currentDepth;
        }

        TreeNode[] children = current.getChildren();

        for (int i = 0; i < children.length; i++) {
            int result = depthRecursive(children[i], nodeId, currentDepth + 1);

            if (result != -1) {
                return result;
            }
        }

        return -1;
    }

    private boolean hasCycle(TreeNode current, TreeNode[] visited, int[] size) {
        if (current == null) {
            return false;
        }

        for (int i = 0; i < size[0]; i++) {
            if (visited[i] == current || visited[i].getId().equals(current.getId())) {
                return true;
            }
        }

        if (size[0] == visited.length) {
            throw new RuntimeException("La validación excede el tamaño máximo permitido");
        }

        visited[size[0]] = current;
        size[0]++;

        TreeNode[] children = current.getChildren();

        for (int i = 0; i < children.length; i++) {
            if (hasCycle(children[i], visited, size)) {
                return true;
            }
        }

        size[0]--;
        return false;
    }

    private TreeNode[] copy(TreeNode[] original, int size) {
        TreeNode[] result = new TreeNode[size];

        for (int i = 0; i < size; i++) {
            result[i] = original[i];
        }

        return result;
    }

    private TreeNode[] resize(TreeNode[] original) {
        TreeNode[] result = new TreeNode[original.length * 2];

        for (int i = 0; i < original.length; i++) {
            result[i] = original[i];
        }

        return result;
    }

    private List<TreeNode> toList(TreeNode[] nodes) {
        List<TreeNode> result = new ArrayList<>();

        for (int i = 0; i < nodes.length; i++) {
            result.add(nodes[i]);
        }

        return result;
    }
}