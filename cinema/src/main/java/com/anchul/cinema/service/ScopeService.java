package com.anchul.cinema.service;

import com.anchul.cinema.model.Scope;
import com.anchul.cinema.repository.ScopeRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;

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

    // 리뷰 한개만 달 수 있도록하는 함수
    public boolean validate(Scope scope) {
        // 집계연산 목록
        List<AggregationOperation> operations = new ArrayList<>();
        // 매치 연산(where)
        operations.add(match(Criteria.where("userId").is(scope.getUserId())
                .and("movieId").is(scope.getMovieId())));

        // 집계연산 목록을 하나의 집계목록으로 만들기
        Aggregation aggregation = Aggregation.newAggregation(operations);

        // 집계 객체에 = (집계파이프라인, 테이블명, 매핑값)
        AggregationResults<Scope> results = MONGO_TEMPLATE.aggregate(aggregation, "scope", Scope.class);
        return results.getUniqueMappedResult()==null;
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

}
