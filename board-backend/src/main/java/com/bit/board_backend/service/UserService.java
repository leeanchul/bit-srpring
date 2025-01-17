package com.bit.board_backend.service;

import com.bit.board_backend.model.UserDTO;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private  SqlSession sqlSession;
    private final String NAMESPACE="mappers.UserMapper";
    // DB 와 통신하여 username, pw 가 같은 회원 찾기
    public UserDTO auth(UserDTO userDTO){
        return sqlSession.selectOne(NAMESPACE+".auth",userDTO);
    }
    public boolean validateUsername(UserDTO userDTO){
        return  sqlSession.selectOne(NAMESPACE+".validateUsername",userDTO)==null;
    }

    public boolean validateNickname(UserDTO userDTO){
        return  sqlSession.selectOne(NAMESPACE+".validateNickname",userDTO)==null;
    }
    // 회원 가입하기


}
