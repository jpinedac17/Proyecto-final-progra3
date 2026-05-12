package com.app.backend.service;

import com.app.backend.repository.TreeRepository;

public class TreeService {

    private final TreeRepository repo;

    public TreeService(TreeRepository repo) {
        this.repo = repo;
    }
}