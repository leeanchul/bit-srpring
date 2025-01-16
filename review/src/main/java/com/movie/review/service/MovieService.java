package com.movie.review.service;

import com.movie.review.model.MovieDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private SqlSession sqlSession;
    private final String NAMESPACE="mappers.MovieMapper";

    public List<MovieDTO> movieAll(){
        return sqlSession.selectList(NAMESPACE+".movieAll");
    }

    public MovieDTO movieOne(int id){
        return sqlSession.selectOne(NAMESPACE+".movieOne",id);
    }

    public void insert(MovieDTO movieDTO){
        sqlSession.insert(NAMESPACE+".insert",movieDTO);
    }

    public void update(MovieDTO movieDTO){
        sqlSession.update(NAMESPACE+".update",movieDTO);
    }

    public void delete(int id){
        sqlSession.delete(NAMESPACE+".delete",id);
    }

}
