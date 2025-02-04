package com.bit.mongobackend.service;

import com.bit.mongobackend.model.User;
import com.bit.mongobackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository USER_REPOSITORY;
    private final String TYPE="user";
    private final SequenceService SEQ_SERVICE;

    public User regisetr(User user){
        user.setId(SEQ_SERVICE.getSequence(TYPE));

        return USER_REPOSITORY.save(user);
    }

    public User findByUsername(String username){
        return USER_REPOSITORY.findByUsername(username);
    }

}
