package com.app.backend.service;

import com.app.backend.model.Node;
import com.app.backend.repository.TreeRepository;
import com.arbol.tree_lib.builder.NodeFlat;
import com.arbol.tree_lib.builder.TreeBuilder;
import com.arbol.tree_lib.model.TreeNode;
import com.arbol.tree_lib.operations.TreeOperations;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeService {

    private final TreeRepository repo;
    private final TreeOperations operations = new TreeOperations();

    private Long idCounter = 1L;

    public TreeService(TreeRepository repo) {
        this.repo = repo;
    }

    // 1. Crear raíz
    public Node createRoot(String value) {
        if (repo.getRoot().isPresent()) {
            throw new RuntimeException("Ya existe una raíz");
        }

        Node root = new Node(idCounter++, value, null);
        return repo.save(root);
    }

    // 2. Agregar hijo
    public Node addChild(Long parentId, String value) {
        Node parent = repo.findById(parentId)
                .orElseThrow(() -> new RuntimeException("No se encontró el nodo padre"));

        Node child = new Node(idCounter++, value, parentId);
        return repo.save(child);
    }

    // 3. Obtener árbol completo
    public TreeNode getTree() {

        List<NodeFlat> flat = repo.findAll().stream()
                .map(n -> new NodeFlat(n.getId(), n.getValue(), n.getParentId()))
                .toList();

        return TreeBuilder.build(flat);
    }

    // 4. Subárbol
    public TreeNode getSubTree(Long nodeId) {
        return operations.getSubTree(getTree(), nodeId);
    }

    // 5. Ruta desde raíz
    public TreeNode[] getPath(Long nodeId) {
        return operations.getPath(getTree(), nodeId);
    }

    // 6 y 7. DFS / BFS
    public TreeNode[] getTraversal(String type) {

        if ("DFS".equalsIgnoreCase(type)) {
            return operations.dfs(getTree());
        } else if ("BFS".equalsIgnoreCase(type)) {
            return operations.bfs(getTree());
        }

        throw new RuntimeException("Tipo de recorrido inválido");
    }

    // 8. Altura
    public int getHeight() {
        return operations.height(getTree());
    }

    // 9. Profundidad
    public int getDepth(Long nodeId) {
        return operations.depth(getTree(), nodeId);
    }

    // 10. Ancestros
    public TreeNode[] getAncestors(Long nodeId) {
        return operations.getAncestors(getTree(), nodeId);
    }

    // 11. Validar árbol
    public boolean validate() {
        return operations.validate(getTree());
    }
}