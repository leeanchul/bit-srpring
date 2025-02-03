package com.example.moviebackend.service;

import com.example.moviebackend.model.ReviewDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private SqlSession sqlSession;
    private final String NAMESPACE = "mappers.ReviewMapper";

    public List<ReviewDTO> reviewAll(int movieId){
        return sqlSession.selectList(NAMESPACE+".reviewAll",movieId);
    }

    public void insert(ReviewDTO reviewDTO){
        sqlSession.insert(NAMESPACE+".insert",reviewDTO);
    }

    public boolean validateUserid(ReviewDTO reviewDTO){
        return sqlSession.selectOne(NAMESPACE+".validateUserid",reviewDTO) == null ;
    }
    public void update(ReviewDTO reviewDTO){
        sqlSession.update(NAMESPACE+".update",reviewDTO);
    }
    public void delete(int id){
        sqlSession.delete(NAMESPACE+".delete",id);
    }
}
