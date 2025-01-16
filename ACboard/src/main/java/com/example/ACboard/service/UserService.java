package com.example.ACboard.service;

import com.example.ACboard.model.UserDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private SqlSession sqlSession;

    private final String NAMESPACE="mappers.UserMapper";
    public UserDTO loadByUsername(String username){

        return sqlSession.selectOne(NAMESPACE+".selectByUsername",username);
    }

    public void register(UserDTO userDTO){
        // DB에 평문에 비밀번호를 암호화해서 넣기 위해서 사용한다.
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        sqlSession.insert(NAMESPACE+".register",userDTO);
    }

    public boolean validateUsername(UserDTO userDTO){
        return sqlSession.selectOne(NAMESPACE+".validateUsername",userDTO) == null;
    }

    public boolean validateNickname(UserDTO userDTO){
        return sqlSession.selectOne(NAMESPACE+".validateNickname",userDTO) == null;
    }


}