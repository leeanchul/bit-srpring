package com.anchul.cinema.service;

import com.anchul.cinema.model.Cinema;
import com.anchul.cinema.model.Movie;
import com.anchul.cinema.repository.CinemaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {
    private final CinemaRepository CINEMA_REPOSITORY;
    private final SequenceService SEQ_SERVICE;
    private final MongoTemplate MONGO_TEMPLATE;
    private final String TYPE="cinema";

    public CinemaService(CinemaRepository cinemaRepository, SequenceService seqService, MongoTemplate mongoTemplate) {
        CINEMA_REPOSITORY = cinemaRepository;
        SEQ_SERVICE = seqService;
        MONGO_TEMPLATE = mongoTemplate;
    }

    // 조회ㅣ
    public List<Cinema> cinemaAll(){
        return CINEMA_REPOSITORY.findAll();
    }

    // page 조회
    public Slice<Cinema> findAllWithUser(Pageable pageable){

        return CINEMA_REPOSITORY.findAllWithUser(pageable);
    }

    // 입력
    public void cinemaInsert(Cinema cinema){
        cinema.setId(SEQ_SERVICE.getSequence(TYPE));
        CINEMA_REPOSITORY.save(cinema);
    }

    // 페이지 총 수
    public long countTotal(){
        return MONGO_TEMPLATE.count(new Query(),Cinema.class);
    }

    // 삭제
    public void deleteById(int id) {
        CINEMA_REPOSITORY.deleteById(id);
    }
}
