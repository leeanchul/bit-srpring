package com.movie.review.service;

import com.movie.review.model.ScopeDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScopeService {
    @Autowired
    private SqlSession sqlSession;
    private final String NAMESPACE="mappers.ScopeMapper";

    public void insert(ScopeDTO scopeDTO){
        sqlSession.insert(NAMESPACE+".insert",scopeDTO);
    }

    public boolean validateUserid(ScopeDTO scopeDTO){
        return sqlSession.selectOne(NAMESPACE+".validateUserid",scopeDTO) == null ;
    }

    public double scoreAll(int movieId){
        List<ScopeDTO> list=sqlSession.selectList(NAMESPACE+".scoreAll",movieId);
        int totalScore=0;
        int userCount=0;
        for(ScopeDTO d:list){
            totalScore+=d.getScore();
            userCount ++;
        }

        return (double) totalScore /userCount;
    }


}
