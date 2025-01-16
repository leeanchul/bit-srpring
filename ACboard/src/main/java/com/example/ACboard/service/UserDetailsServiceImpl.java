package com.example.ACboard.service;

import com.example.ACboard.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 이 메소드는 사용자가 로그인 요청을 보내면
        // 자동으로 호출되며
        // 우리 DB에서 해당 username을 가진 사용자를 불러와서
        // 로그인 정보가 정확한지 비교하여
        // 일치하는 회원을 반환한다.
        UserDTO userDTO=userService.loadByUsername(username);
        System.out.println(userDTO);

        return userDTO;
    }


}