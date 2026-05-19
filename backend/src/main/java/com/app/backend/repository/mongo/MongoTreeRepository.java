package com.app.backend.repository.mongo;

import com.app.backend.model.Node;
import com.app.backend.repository.TreeRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
@Primary
public class MongoTreeRepository implements TreeRepository {

    private final MongoNodeSpringRepository mongoRepo;

    public MongoTreeRepository(MongoNodeSpringRepository mongoRepo) {
        this.mongoRepo = mongoRepo;
    }

    @Override
    public Node save(Node node) {
        MongoNodeDocument saved = mongoRepo.save(MongoNodeMapper.toDocument(node));
        return MongoNodeMapper.toDomain(saved);
    }

    @Override
    public Optional<Node> findById(Long id) {
        return mongoRepo.findById(id)
                .map(MongoNodeMapper::toDomain);
    }

    @Override
    public List<Node> findAll() {
        return mongoRepo.findAll()
                .stream()
                .map(MongoNodeMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Node> getRoot() {
        return mongoRepo.findByParentIdIsNull()
                .map(MongoNodeMapper::toDomain);
    }
}
