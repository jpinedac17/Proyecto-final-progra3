package com.app.backend.repository;

import com.app.backend.model.Node;

import java.util.List;
import java.util.Optional;

public interface TreeRepository {

    Node save(Node node);

    Optional<Node> findById(Long id);

    List<Node> findAll();

    Optional<Node> getRoot();
}
