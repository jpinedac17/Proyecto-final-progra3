package com.app.backend.repository;

import com.app.backend.model.Node;

import java.util.*;

public class MemoryRepository implements TreeRepository {

	// Esto es temporal, la implementación real se trabajará en la semana 2
    @Override
    public Node save(Node node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Node> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Node> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Optional<Node> getRoot() {
        return Optional.empty();
    }
}
