package com.bit.mongobackend.service;

import com.bit.mongobackend.model.Reply;
import com.bit.mongobackend.repository.ReplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@AllArgsConstructor
public class ReplyService {
    private final String TYPE = "reply";
    private final SequenceService SEQ_SERVICE;
    private final ReplyRepository REPLY_REPOSITORY;
    private final MongoOperations OPERTIONS;
    private final MongoTemplate mongoTemplate;

    public Reply insert(Reply reply) {
        reply.setId(SEQ_SERVICE.getSequence(TYPE));
        return REPLY_REPOSITORY.save(reply);
    }

//    public List<Reply> findReplyBy(int boardId){
//        Query query=new Query(Criteria.where("boardId").is(boardId));
//        return OPERTIONS.find(query,Reply.class);
//    }
public List<Reply> findReplyBy(int boardId) {
    List<AggregationOperation> operations = new ArrayList<>();

    operations.add(match(Criteria.where("boardId").is(boardId)));
    operations.add(lookup("user", "writerId", "_id", "writer"));
    operations.add(unwind("writer"));
    operations.add(project("id", "boardId", "content", "entryDate", "modifyDate", "writerId")
            .and("writer.nickname").as("writerNickname"));

    Aggregation aggregation = Aggregation.newAggregation(operations);

    AggregationResults<Reply> results = mongoTemplate.aggregate(aggregation, "reply", Reply.class);
    return results.getMappedResults();
}
    public void delete(int id){
        REPLY_REPOSITORY.deleteById(id);
    }
    public Reply update(Reply reply){
        Query query=new Query(Criteria.where("_id").is(reply.getId()));

        Update update=new Update()
                .set("content",reply.getContent())
                .set("modifyDate",new Date());
        return OPERTIONS.findAndModify(query,update,Reply.class);
    }
}
