package com.app.backend.controller;

import com.app.backend.service.TreeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nodes")
public class TreeController {

    private final TreeService service;

    public TreeController(TreeService service) {
        this.service = service;
    }
}