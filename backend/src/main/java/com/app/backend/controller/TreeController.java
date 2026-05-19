package com.app.backend.controller;

import com.app.backend.api.NodesApi;
import com.app.backend.api.TreeApi;

import com.app.backend.dto.CreateNodeRequest;
import com.app.backend.dto.DepthResponse;
import com.app.backend.dto.HeightResponse;
import com.app.backend.dto.NodeResponse;
import com.app.backend.dto.NodeSimpleResponse;
import com.app.backend.dto.TreeNodeResponse;
import com.app.backend.dto.ValidateResponse;

import com.app.backend.model.Node;
import com.app.backend.service.TreeService;

import com.arbol.tree_lib.model.TreeNode;

import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TreeController implements NodesApi, TreeApi {

    private final TreeService service;

    public TreeController(TreeService service) {
        this.service = service;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    // 1. Crear raíz
    @Override
    public ResponseEntity<NodeResponse> createRoot(CreateNodeRequest request) {

        Node node = service.createRoot(request.getValue());

        return ResponseEntity.ok(toNodeResponse(node));
    }

    // 2. Agregar hijo
    @Override
    public ResponseEntity<NodeResponse> addChild(Long parentId,
                                                 CreateNodeRequest request) {

        Node node = service.addChild(parentId, request.getValue());

        return ResponseEntity.ok(toNodeResponse(node));
    }

    // 3. Obtener árbol completo
    @Override
    public ResponseEntity<TreeNodeResponse> getTree() {

        TreeNode root = service.getTree();

        return ResponseEntity.ok(toTreeNodeResponse(root));
    }

    // 4. Obtener subárbol
    @Override
    public ResponseEntity<TreeNodeResponse> getSubTree(Long nodeId) {

        TreeNode subtree = service.getSubTree(nodeId);

        return ResponseEntity.ok(toTreeNodeResponse(subtree));
    }

    // 5. Ruta desde raíz
    @Override
    public ResponseEntity<List<NodeSimpleResponse>> getPath(Long nodeId) {

        TreeNode[] path = service.getPath(nodeId);

        return ResponseEntity.ok(toSimpleList(path));
    }

    // 6 y 7. DFS / BFS
    @Override
    public ResponseEntity<List<NodeSimpleResponse>> getTraversal(String type) {

        TreeNode[] traversal = service.getTraversal(type);

        return ResponseEntity.ok(toSimpleList(traversal));
    }

    // 8. Altura
    @Override
    public ResponseEntity<HeightResponse> getHeight() {

        HeightResponse response = new HeightResponse();

        response.setHeight(service.getHeight());

        return ResponseEntity.ok(response);
    }

    // 9. Profundidad
    @Override
    public ResponseEntity<DepthResponse> getDepth(Long nodeId) {

        DepthResponse response = new DepthResponse();

        response.setDepth(service.getDepth(nodeId));

        return ResponseEntity.ok(response);
    }

    // 10. Ancestros
    @Override
    public ResponseEntity<List<NodeSimpleResponse>> getAncestors(Long nodeId) {

        TreeNode[] ancestors = service.getAncestors(nodeId);

        return ResponseEntity.ok(toSimpleList(ancestors));
    }

    // 11. Validar árbol
    @Override
    public ResponseEntity<ValidateResponse> validateTree() {

        boolean hasCycle = service.validate();

        ValidateResponse response = new ValidateResponse();

        response.setHasCycle(hasCycle);
        response.setValid(!hasCycle);

        return ResponseEntity.ok(response);
    }

    // MAPPERS
    private NodeResponse toNodeResponse(Node node) {

        NodeResponse response = new NodeResponse();

        response.setId(node.getId());
        response.setValue(node.getValue());
        response.setParentId(JsonNullable.of(node.getParentId()));

        return response;
    }

    private TreeNodeResponse toTreeNodeResponse(TreeNode node) {

        if (node == null) {
            return null;
        }

        TreeNodeResponse response = new TreeNodeResponse();

        response.setId(node.getId());
        response.setValue(node.getValue());

        List<TreeNodeResponse> children = new ArrayList<>();

        TreeNode[] nodeChildren = node.getChildren();

        for (int i = 0; i < nodeChildren.length; i++) {
            children.add(toTreeNodeResponse(nodeChildren[i]));
        }

        response.setChildren(children);

        return response;
    }

    private List<NodeSimpleResponse> toSimpleList(TreeNode[] nodes) {

        List<NodeSimpleResponse> response = new ArrayList<>();

        for (int i = 0; i < nodes.length; i++) {

            NodeSimpleResponse simple = new NodeSimpleResponse();

            simple.setId(nodes[i].getId());
            simple.setValue(nodes[i].getValue());

            response.add(simple);
        }

        return response;
    }
}