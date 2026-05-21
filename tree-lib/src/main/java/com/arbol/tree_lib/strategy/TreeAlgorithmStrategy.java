package com.arbol.tree_lib.strategy;

import com.arbol.tree_lib.model.TreeNode;

import java.util.List;
import java.util.Optional;

public interface TreeAlgorithmStrategy {

    String strategyName();

    Optional<TreeNode> findSubTree(TreeNode root, Long nodeId);

    List<TreeNode> pathFromRoot(TreeNode root, Long nodeId);

    List<TreeNode> dfs(TreeNode root);

    List<TreeNode> bfs(TreeNode root);

    int height(TreeNode root);

    int depth(TreeNode root, Long nodeId);

    List<TreeNode> ancestors(TreeNode root, Long nodeId);

    boolean validateNoCycles(TreeNode root);
}