package com.bit.mongobackend.repository;

import com.bit.mongobackend.model.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SequenceRepository extends MongoRepository<Sequence, String> {

}
