package com.app.backend.repository;

import com.app.backend.model.Node;
import java.util.*;

public class MemoryRepository implements TreeRepository {

    private final Map<Long, Node> storage = new HashMap<>();

    @Override
    public Node save(Node node) {
        storage.put(node.getId(), node);
        return node;
    }

    @Override
    public Optional<Node> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Node> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Node> getRoot() {
        return storage.values().stream()
                .filter(n -> n.getParentId() == null)
                .findFirst();
    }
}
