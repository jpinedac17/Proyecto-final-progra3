package com.app.backend.repository.postgres;

import com.app.backend.model.Node;
import com.app.backend.repository.TreeRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("postgres")
@Primary
public class PostgresRepository implements TreeRepository {

    private final NodeJpaRepository nodeJpaRepository;

    public PostgresRepository(NodeJpaRepository nodeJpaRepository) {
        this.nodeJpaRepository = nodeJpaRepository;
    }

    @Override
    public Node save(Node node) {
        NodeEntity saved = nodeJpaRepository.save(NodeEntityMapper.toEntity(node));
        return NodeEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Node> findById(Long id) {
        return nodeJpaRepository.findById(id)
                .map(NodeEntityMapper::toDomain);
    }

    @Override
    public List<Node> findAll() {
        return nodeJpaRepository.findAll()
                .stream()
                .map(NodeEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Node> getRoot() {
        return nodeJpaRepository.findByParentIdIsNull()
                .map(NodeEntityMapper::toDomain);
    }
}