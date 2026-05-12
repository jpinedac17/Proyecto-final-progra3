package com.app.backend.controller;

import com.app.backend.model.CreateNodeRequest;
import com.app.backend.service.TreeService;
import com.arbol.tree_lib.model.TreeNode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nodes")
public class TreeController {

    private final TreeService service;

    public TreeController(TreeService service) {
        this.service = service;
    }

    @PostMapping("/root")
    public Object createRoot(@RequestBody CreateNodeRequest request) {
        return service.createRoot(request.value);
    }

    @PostMapping("/{parentId}/children")
    public Object addChild(@PathVariable Long parentId, @RequestBody CreateNodeRequest request) {
    	return service.addChild(parentId, request.value);
    }

    @GetMapping("/tree")
    public TreeNode getTree() {
        return service.getTree();
    }
    
    @GetMapping("/ping")
    public String ping() {
        return "ok";
    }
}