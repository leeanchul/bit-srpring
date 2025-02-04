package com.bit.mongobackend.repository;

import com.bit.mongobackend.model.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {

    @Aggregation(pipeline = {
            "{$lookup: { from: 'user',localField:'writerId',foreignField: '_id',as: 'writer'}}",
            "{$unwind: '$writer' }",
            "{ $project: {id:1,title: 1,content: 1,entryDate: 1,modifyDate: 1,writerId: 1,writerNickname: '$writer.nickname'} }"
    })
    Slice<Board> findAllWithUser(Pageable pageable);

    void deleteById(int id);
}
