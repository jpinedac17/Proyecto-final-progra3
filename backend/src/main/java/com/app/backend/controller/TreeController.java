package com.app.backend.controller;

import com.app.backend.model.CreateNodeRequest;
import com.app.backend.service.TreeService;
import com.arbol.tree_lib.model.TreeNode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class TreeController {

    private final TreeService service;

    public TreeController(TreeService service) {
        this.service = service;
    }

    // 1. Crear raíz
    @PostMapping("/nodes/root")
    public Object createRoot(@RequestBody CreateNodeRequest request) {
        return service.createRoot(request.value);
    }

    // 2. Agregar hijo
    @PostMapping("/nodes/{parentId}/children")
    public Object addChild(@PathVariable Long parentId,
                           @RequestBody CreateNodeRequest request) {
        return service.addChild(parentId, request.value);
    }

    // 3. Obtener árbol completo
    @GetMapping("/tree")
    public TreeNode getTree() {
        return service.getTree();
    }

    // 4. Subárbol
    @GetMapping("/tree/{id}")
    public TreeNode getSubTree(@PathVariable Long id) {
        return service.getSubTree(id);
    }

    // 5. Ruta desde raíz
    @GetMapping("/nodes/{id}/path")
    public TreeNode[] getPath(@PathVariable Long id) {
        return service.getPath(id);
    }

    // 6 y 7. DFS / BFS
    @GetMapping("/tree/traversal")
    public TreeNode[] traversal(@RequestParam String type) {
        return service.getTraversal(type);
    }

    // 8. Altura
    @GetMapping("/tree/height")
    public int height() {
        return service.getHeight();
    }

    // 9. Profundidad
    @GetMapping("/nodes/{id}/depth")
    public int depth(@PathVariable Long id) {
        return service.getDepth(id);
    }

    // 10. Ancestros
    @GetMapping("/nodes/{id}/ancestors")
    public TreeNode[] ancestors(@PathVariable Long id) {
        return service.getAncestors(id);
    }

    // 11. Validar árbol
    @GetMapping("/tree/validate")
    public boolean validate() {
        return service.validate();
    }

    // Extra (health check)
    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}