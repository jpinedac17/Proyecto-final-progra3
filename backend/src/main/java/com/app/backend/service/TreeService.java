package com.app.backend.service;

import com.app.backend.model.Node;
import com.app.backend.repository.TreeRepository;
import com.arbol.tree_lib.builder.NodeFlat;
import com.arbol.tree_lib.builder.TreeBuilder;
import com.arbol.tree_lib.model.TreeNode;

import java.util.List;

public class TreeService {

    private final TreeRepository repo;
    private Long idCounter = 1L;

    public TreeService(TreeRepository repo) {
        this.repo = repo;
    }

    public Node createRoot(String value) {
        if (repo.getRoot().isPresent()) {
            throw new RuntimeException("Root already exists");
        }

        Node root = new Node(idCounter++, value, null);
        return repo.save(root);
    }

    public Node addChild(Long parentId, String value) {
        Node parent = repo.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        Node child = new Node(idCounter++, value, parentId);
        return repo.save(child);
    }

    public TreeNode getTree() {

        List<NodeFlat> flat = repo.findAll().stream()
                .map(n -> new NodeFlat(n.getId(), n.getValue(), n.getParentId()))
                .toList();

        return TreeBuilder.build(flat);
    }
}