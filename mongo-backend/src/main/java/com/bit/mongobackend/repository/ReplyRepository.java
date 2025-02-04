package com.bit.mongobackend.repository;

import com.bit.mongobackend.model.Reply;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReplyRepository extends MongoRepository<Reply,String> {
    //List<Reply> findReplyBy(int pageNo);
    void deleteById(int id);
}
