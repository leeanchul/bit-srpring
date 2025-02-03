package com.example.moviebackend.service;


import com.example.moviebackend.model.TokenDTO;
import com.example.moviebackend.model.UserDTO;
import com.example.moviebackend.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtProvider jwtProvider;

    public TokenDTO auth(UserDTO userDTO){

        UserDTO origin= (UserDTO) userDetailsService.loadUserByUsername(userDTO.getUsername());


        if(origin == null){
            throw new UsernameNotFoundException("존재하지 않은 회원입니다.");
        }

        if(! passwordEncoder.matches(userDTO.getPassword(), origin.getPassword())   ){
            throw new BadCredentialsException("로그인 정보를 다시 확인해주세요");
        }

        TokenDTO tokenDTO=jwtProvider.generate(origin);

        return tokenDTO;
    }

}
