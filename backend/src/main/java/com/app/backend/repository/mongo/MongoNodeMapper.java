package com.app.backend.repository.mongo;

import com.app.backend.model.Node;
import org.springframework.context.annotation.Profile;

@Profile("mongo")
public final class MongoNodeMapper {

    private MongoNodeMapper() {
    }

    public static MongoNodeDocument toDocument(Node node) {
        return new MongoNodeDocument(
                node.getId(),
                node.getValue(),
                node.getParentId()
        );
    }

    public static Node toDomain(MongoNodeDocument document) {
        return new Node(
                document.getId(),
                document.getValue(),
                document.getParentId()
        );
    }
}
