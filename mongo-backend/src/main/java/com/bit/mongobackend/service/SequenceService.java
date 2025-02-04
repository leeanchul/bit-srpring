package com.bit.mongobackend.service;

import com.bit.mongobackend.model.Sequence;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SequenceService {
    private final MongoOperations OPERATIONS;

    public int getSequence(String type){
        // Sequence table 에서 _id 컬럼이 type 과 같은 것을 찾아라.
        Query query=new Query(Criteria.where("_id").is(type));
        Update update = new Update().inc("seq",1);

        // 세부적인 옵션
        // returnNew : true 값이 바뀐 후의 새로운 값을 return, false update 전 저장된 값을 return
        // upsert: true 우리가 찾고자 하는 것이 없으면 새로 생성해라
        FindAndModifyOptions options=FindAndModifyOptions.options().returnNew(false).upsert(true);

        Sequence sequence =OPERATIONS.findAndModify(query,update,options, Sequence.class);

        return sequence.getSeq();
    }
}
