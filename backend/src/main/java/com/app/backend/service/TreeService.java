package com.app.backend.service;

import com.app.backend.model.Node;
import com.app.backend.repository.TreeRepository;
import com.arbol.tree_lib.builder.NodeFlat;
import com.arbol.tree_lib.builder.TreeBuilder;
import com.arbol.tree_lib.model.TreeNode;
import com.arbol.tree_lib.strategy.TreeAlgorithmStrategy;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeService {

    private final TreeRepository repo;
    private final TreeAlgorithmStrategy strategy;

    private Long idCounter = 1L;

    public TreeService(TreeRepository repo, TreeAlgorithmStrategy strategy) {
        this.repo = repo;
        this.strategy = strategy;
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
        repo.findById(parentId)
                .orElseThrow(() -> new RuntimeException("No se encontró el nodo padre"));

        Node child = new Node(idCounter++, value, parentId);
        return repo.save(child);
    }

    // 3. Obtener árbol completo
    public TreeNode getTree() {

        List<Node> nodes = repo.findAll();

        if (nodes.isEmpty()) {
            throw new RuntimeException("El árbol está vacío");
        }

        List<NodeFlat> flat = nodes.stream()
                .map(n -> new NodeFlat(n.getId(), n.getValue(), n.getParentId()))
                .toList();

        return TreeBuilder.build(flat);
    }

    // 4. Subárbol
    public TreeNode getSubTree(Long nodeId) {
        return strategy.findSubTree(getTree(), nodeId)
                .orElseThrow(() -> new RuntimeException("No se encontró el nodo"));
    }

    // 5. Ruta desde raíz
    public TreeNode[] getPath(Long nodeId) {
        return strategy.pathFromRoot(getTree(), nodeId)
                .toArray(new TreeNode[0]);
    }

    // 6 y 7. DFS / BFS
    public TreeNode[] getTraversal(String type) {

        if ("DFS".equalsIgnoreCase(type)) {
            return strategy.dfs(getTree()).toArray(new TreeNode[0]);
        } else if ("BFS".equalsIgnoreCase(type)) {
            return strategy.bfs(getTree()).toArray(new TreeNode[0]);
        }

        throw new RuntimeException("Tipo de recorrido inválido");
    }

    // 8. Altura
    public int getHeight() {
        return strategy.height(getTree());
    }

    // 9. Profundidad
    public int getDepth(Long nodeId) {
        return strategy.depth(getTree(), nodeId);
    }

    // 10. Ancestros
    public TreeNode[] getAncestors(Long nodeId) {
        return strategy.ancestors(getTree(), nodeId)
                .toArray(new TreeNode[0]);
    }

    // 11. Validar árbol
    public boolean validate() {
        return strategy.validateNoCycles(getTree());
    }
}