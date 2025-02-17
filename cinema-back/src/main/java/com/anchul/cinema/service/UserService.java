package com.anchul.cinema.service;

import com.anchul.cinema.model.Movie;
import com.anchul.cinema.model.User;
import com.anchul.cinema.repository.UserRepository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository USER_REPOSITORY;
    private final SequenceService SEQ_SERVICE;
    private final String TYPE="user";
    private final MongoTemplate MONGO_TEMPLATE;
    
    public UserService(UserRepository userRepository, SequenceService seqService,MongoTemplate mongoTemplate) {
        USER_REPOSITORY = userRepository;
        SEQ_SERVICE = seqService;
        MONGO_TEMPLATE=mongoTemplate;
    }

    public User findByUsername(String username){
        return USER_REPOSITORY.findByUsername(username);
    }

    public User register(User user){
        user.setId(SEQ_SERVICE.getSequence(TYPE));

        return USER_REPOSITORY.save(user);
    }
    
    public boolean vaildateNickname(String newNickname) {
    	Query query=new Query(Criteria.where("nickname").is(newNickname));
    	return MONGO_TEMPLATE.findOne(query, User.class) == null;
    }
    
    public void updateNickname(User user) {
    	Query query=new Query(Criteria.where("_id").is(user.getId()));
    	Update update = new Update()
    			.set("nickname",user.getNewNickname());
    	
    	 MONGO_TEMPLATE.findAndModify(query, update, User.class);
    }
}
