package com.app.backend.repository.postgres;

import org.springframework.context.annotation.Profile;

import com.app.backend.model.Node;

@Profile("postgres")
public final class NodeEntityMapper {

    private NodeEntityMapper() {
    }

    public static NodeEntity toEntity(Node node) {
        return new NodeEntity(
                node.getId(),
                node.getValue(),
                node.getParentId()
        );
    }

    public static Node toDomain(NodeEntity entity) {
        return new Node(
                entity.getId(),
                entity.getValue(),
                entity.getParentId()
        );
    }
}