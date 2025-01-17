package com.study.ACboard.service;

import com.study.ACboard.model.UserDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final SqlSession sqlSession;

    private final String NAMESPACE="mappers.UserMapper";

    public UserService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public UserDTO loadByUsername(String username){
        return sqlSession.selectOne(NAMESPACE+".selectByUsername",username);
    }

}
