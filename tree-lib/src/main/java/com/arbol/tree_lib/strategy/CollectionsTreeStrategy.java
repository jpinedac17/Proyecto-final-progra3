package com.arbol.tree_lib.strategy;

import com.arbol.tree_lib.model.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

public class CollectionsTreeStrategy implements TreeAlgorithmStrategy {

    @Override
    public String strategyName() {
        return "collections";
    }

    @Override
    public Optional<TreeNode> findSubTree(TreeNode root, Long nodeId) {

        if (root == null || nodeId == null) {
            return Optional.empty();
        }

        if (root.getId().equals(nodeId)) {
            return Optional.of(root);
        }

        for (TreeNode child : root.getChildren()) {

            Optional<TreeNode> found = findSubTree(child, nodeId);

            if (found.isPresent()) {
                return found;
            }
        }

        return Optional.empty();
    }

    @Override
    public List<TreeNode> pathFromRoot(TreeNode root, Long nodeId) {

        List<TreeNode> path = new ArrayList<>();

        buildPath(root, nodeId, path);

        return path;
    }

    private boolean buildPath(
            TreeNode current,
            Long nodeId,
            List<TreeNode> path) {

        if (current == null) {
            return false;
        }

        path.add(current);

        if (current.getId().equals(nodeId)) {
            return true;
        }

        for (TreeNode child : current.getChildren()) {

            if (buildPath(child, nodeId, path)) {
                return true;
            }
        }

        path.remove(path.size() - 1);

        return false;
    }

    @Override
    public List<TreeNode> dfs(TreeNode root) {

        List<TreeNode> result = new ArrayList<>();

        dfsRecursive(root, result);

        return result;
    }

    private void dfsRecursive(
            TreeNode node,
            List<TreeNode> result) {

        if (node == null) {
            return;
        }

        result.add(node);

        for (TreeNode child : node.getChildren()) {
            dfsRecursive(child, result);
        }
    }

    @Override
    public List<TreeNode> bfs(TreeNode root) {

        List<TreeNode> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();

        queue.add(root);

        while (!queue.isEmpty()) {

            TreeNode current = queue.poll();

            result.add(current);

            for (TreeNode child : current.getChildren()) {
                queue.add(child);
            }
        }

        return result;
    }

    @Override
    public int height(TreeNode root) {

        if (root == null || root.getChildren().length == 0) {
            return 0;
        }

        int maxHeight = 0;

        for (TreeNode child : root.getChildren()) {

            maxHeight = Math.max(
                    maxHeight,
                    height(child)
            );
        }

        return maxHeight + 1;
    }

    @Override
    public int depth(TreeNode root, Long nodeId) {

        return depthRecursive(root, nodeId, 0);
    }

    private int depthRecursive(
            TreeNode current,
            Long nodeId,
            int currentDepth) {

        if (current == null) {
            return -1;
        }

        if (current.getId().equals(nodeId)) {
            return currentDepth;
        }

        for (TreeNode child : current.getChildren()) {

            int result = depthRecursive(
                    child,
                    nodeId,
                    currentDepth + 1
            );

            if (result != -1) {
                return result;
            }
        }

        return -1;
    }

    @Override
    public List<TreeNode> ancestors(
            TreeNode root,
            Long nodeId) {

        List<TreeNode> path = pathFromRoot(root, nodeId);

        if (!path.isEmpty()) {

            path.remove(path.size() - 1);
        }

        return path;
    }

    @Override
    public boolean validateNoCycles(TreeNode root) {

        return validateRecursive(root, new HashSet<>());
    }

    private boolean validateRecursive(
            TreeNode current,
            Set<Long> visited) {

        if (current == null) {
            return true;
        }

        if (visited.contains(current.getId())) {
            return false;
        }

        visited.add(current.getId());

        for (TreeNode child : current.getChildren()) {

            if (!validateRecursive(child, visited)) {
                return false;
            }
        }

        visited.remove(current.getId());

        return true;
    }
}