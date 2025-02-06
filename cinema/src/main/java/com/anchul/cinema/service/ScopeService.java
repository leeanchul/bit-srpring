package com.anchul.cinema.service;

import com.anchul.cinema.model.Scope;
import com.anchul.cinema.repository.ScopeRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class ScopeService {
    private final ScopeRepository SCOPE_REPOSITORY;
    private final SequenceService SEQ_SERVICE;
    private final String TYPE = "scope";
    private final MongoTemplate MONGO_TEMPLATE;

    public ScopeService(ScopeRepository scopeRepository, SequenceService seqService, MongoTemplate mongoTemplate) {
        SCOPE_REPOSITORY = scopeRepository;
        SEQ_SERVICE = seqService;
        MONGO_TEMPLATE = mongoTemplate;
    }


    public void scoreInsert(Scope scope) {
        scope.setId(SEQ_SERVICE.getSequence(TYPE));
        SCOPE_REPOSITORY.save(scope);
    }

    public Scope validate(Scope scope) {
        // 집계연산 목록
        List<AggregationOperation> operations = new ArrayList<>();
        // 매치 연산(where)
        operations.add(match(Criteria.where("userId").is(scope.getUserId())
                .and("movieId").is(scope.getMovieId())));

        // 집계연산 목록을 하나의 집계목록으로 만들기
        Aggregation aggregation = Aggregation.newAggregation(operations);

        // 집계 객체에 = (집계파이프라인, 테이블명, 매핑값)
        AggregationResults<Scope> results = MONGO_TEMPLATE.aggregate(aggregation, "scope", Scope.class);
        return results.getUniqueMappedResult();
    }

    public Scope scopeAvg(Scope scope) {
        List<AggregationOperation> operations = new ArrayList<>();
        // Scope에서 movieId랑 movieId 가 같은것을 매치

        // null 이면 전체를 조회
        if (scope.getUserRole() == null) {
            operations.add(match(Criteria.where("movieId").is(scope.getMovieId())));
        } else {
            operations.add(match(Criteria.where("movieId").is(scope.getMovieId())
                    .and("userRole").is(scope.getUserRole())
            ));
        }
        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<Scope> results = MONGO_TEMPLATE.aggregate(aggregation, "scope", Scope.class);
        List<Scope> list = results.getMappedResults();
        double sum = 0;
        for (Scope s : list) {
            sum += s.getScore();
        }
        Scope result = new Scope();
        result.setMaxAvg(sum / list.size());
        result.setCount(list.size());
        return result;
    }

    public List<Scope> reviewAll(int movieId) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.add(match(Criteria.where("movieId").is(movieId)));
        //조인 연산 (lookup) (join) 절) : 조인할 대상 , 조인할때 사용할 필드, 조인할대상에 필드, 조인 저장할 필드
        operations.add(lookup("user", "userId", "_id", "writer"));
        // 배열로 분해하기
        operations.add(unwind("writer"));
        // prject 연산
        operations.add(project("id", "movieId", "review", "entryDate")
                .and("writer.nickname").as("nickname"));
        Aggregation aggregation = Aggregation.newAggregation(operations);
        AggregationResults<Scope> results = MONGO_TEMPLATE.aggregate(aggregation, "scope", Scope.class);

        return results.getMappedResults();
    }

    public void deleteById(int id) {
        SCOPE_REPOSITORY.deleteById(id);
    }

    public Scope ScopeOne(int id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return MONGO_TEMPLATE.findOne(query, Scope.class);
    }


    public void reviewInsert(Scope scope) {
        scope.setId(SEQ_SERVICE.getSequence(TYPE));
        SCOPE_REPOSITORY.save(scope);
    }

    public void reviewUpdate(Scope scope) {
        Query query = new Query(Criteria.where("_id").is(scope.getId()));

        Update update = new Update()
                .set("review", scope.getReview());
        MONGO_TEMPLATE.findAndModify(query, update, Scope.class);
    }
    public void scoreUpdate(Scope scope) {
        Query query = new Query(Criteria.where("_id").is(scope.getId()));

        Update update = new Update()
                .set("score", scope.getScore());
        MONGO_TEMPLATE.findAndModify(query, update, Scope.class);
    }
}
