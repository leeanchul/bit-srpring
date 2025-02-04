package com.bit.mongobackend.repository;

import com.bit.mongobackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findByUsername(String username);
    User findByNickname(String nickname);

}
