package com.example.moviebackend.service;

import com.example.moviebackend.model.MovieDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private SqlSession sqlSession;
    private final String NAMESPACE = "mappers.MovieMapper";
    private final int SIZE = 5;
    public List<MovieDTO> movieAll(){
        return sqlSession.selectList(NAMESPACE+".movieAll");
    }

    // 페이지에 따른 글 목록 불러오기
    public List<MovieDTO> selectByPage(int page) {
        HashMap<String, Integer> paramMap = new HashMap<>();
        paramMap.put("startRow", (page - 1) * SIZE);
        paramMap.put("limitSize", SIZE);

        return sqlSession.selectList(NAMESPACE + ".selectByPage", paramMap);
    }
    // 최대 페이지 불러오기
    public int maxPage(){
        int temp=sqlSession.selectOne(NAMESPACE+".maxPage");
        return temp % SIZE == 0 ? temp / SIZE : (temp / SIZE) + 1;
    }

    public void insert(MovieDTO movieDTO){
        sqlSession.insert(NAMESPACE+".insert",movieDTO);
    }

    public MovieDTO movieOne(int id){
        return sqlSession.selectOne(NAMESPACE+".movieOne",id);
    }
    public void delete(int id){
        sqlSession.delete(NAMESPACE+".delete",id);
    }
    public void update(MovieDTO movieDTO){
        sqlSession.update(NAMESPACE+".update",movieDTO);
    }
}
