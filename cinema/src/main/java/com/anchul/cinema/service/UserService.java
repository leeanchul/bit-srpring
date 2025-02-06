package com.anchul.cinema.service;

import com.anchul.cinema.model.User;
import com.anchul.cinema.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository USER_REPOSITORY;
    private final SequenceService SEQ_SERVICE;
    private final String TYPE="user";

    public UserService(UserRepository userRepository, SequenceService seqService) {
        USER_REPOSITORY = userRepository;
        SEQ_SERVICE = seqService;
    }

    public User findByUsername(String username){
        return USER_REPOSITORY.findByUsername(username);
    }

    public User register(User user){
        user.setId(SEQ_SERVICE.getSequence(TYPE));

        return USER_REPOSITORY.save(user);
    }
}
