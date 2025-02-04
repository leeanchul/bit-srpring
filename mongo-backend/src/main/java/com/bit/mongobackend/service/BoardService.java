package com.bit.mongobackend.service;

import com.bit.mongobackend.model.Board;
import com.bit.mongobackend.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
public class BoardService {
    private final BoardRepository BOARD_REPOSITORY;
    private final SequenceService SEQ_SERVICE;
    private final String TYPE = "board";
    private final MongoOperations OPERTIONS;
    private final MongoTemplate mongoTemplate;


    public Slice<Board> findAllWithUser(Pageable pageable){
        return BOARD_REPOSITORY.findAllWithUser(pageable);
    }
    public Board insert(Board board) {
        board.setId(SEQ_SERVICE.getSequence(TYPE));
        return BOARD_REPOSITORY.save(board);
    }

    public Board update(Board board) {
        Query query = new Query(Criteria.where("_id").is(board.getId()));

        Update update = new Update()
                .set("title", board.getTitle())
                .set("content", board.getContent())
                .set("modifyDate", new Date());
        return OPERTIONS.findAndModify(query, update, Board.class);
    }

//    public Board selectOne(int pageNo){
//        Query query=new Query(Criteria.where("_id").is(pageNo));
//        return OPERTIONS.findOne(query, Board.class);
//    }
public Board selectOne(int pageNo) {
    List<AggregationOperation> operations = new ArrayList<>();

    //매치 연산 (match):
    operations.add(match(Criteria.where("_id").is(pageNo)));
    //조인 연산 (lookup)
    operations.add(lookup("user", "writerId", "_id", "writer"));
    // 배열로 분해
    operations.add(unwind("writer"));
    operations.add(project("id", "title", "content", "entryDate", "modifyDate", "writerId")
            .and("writer.nickname").as("writerNickname"));

    Aggregation aggregation = Aggregation.newAggregation(operations);

    AggregationResults<Board> results = mongoTemplate.aggregate(aggregation, "board", Board.class);
    return results.getUniqueMappedResult();
}
    public void delete(int id) {
        BOARD_REPOSITORY.deleteById(id);
    }

    public long countTotal() {
        return OPERTIONS.count(new Query(), Board.class);
    }

}
