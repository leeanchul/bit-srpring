package com.example.moviebackend.service;

import com.example.moviebackend.model.ScopeDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScopeService {
    @Autowired
    private SqlSession sqlSession;
    private final String NAMESPACE="mappers.ScopeMapper";

    public Double scopeAll(int movieId){
        List<ScopeDTO> list=sqlSession.selectList(NAMESPACE+".scopeAll",movieId);
        double totalScore=0;
        for(ScopeDTO s: list){
            totalScore+=s.getScore();
        }
        return totalScore/list.size();
    }

    public int count(int movieId){
        List<ScopeDTO> list=sqlSession.selectList(NAMESPACE+".scopeAll",movieId);
        return list.size();
    }
    public int count(ScopeDTO scopeDTO){
        List<ScopeDTO> list=sqlSession.selectList(NAMESPACE+".scopeRole",scopeDTO);
        return list.size();
    }

    public Double scopeRole(ScopeDTO scopeDTO){
        List<ScopeDTO> list=sqlSession.selectList(NAMESPACE+".scopeRole",scopeDTO);
        double totalScore=0;
        for(ScopeDTO s: list){
            totalScore+=s.getScore();
        }
        return totalScore/list.size();
    }

    public boolean validate(ScopeDTO scopeDTO){
        return sqlSession.selectOne(NAMESPACE+".validate",scopeDTO) == null ;
    }

    public void insert(ScopeDTO scopeDTO){
        sqlSession.insert(NAMESPACE+".insert",scopeDTO);
    }
}
