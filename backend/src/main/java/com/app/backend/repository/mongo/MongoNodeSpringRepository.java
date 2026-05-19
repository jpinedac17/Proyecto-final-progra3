package com.app.backend.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("mongo")
public interface MongoNodeSpringRepository extends MongoRepository<MongoNodeDocument, Long> {

    Optional<MongoNodeDocument> findByParentIdIsNull();
}
