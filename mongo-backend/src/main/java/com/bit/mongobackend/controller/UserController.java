package com.bit.mongobackend.controller;

import com.bit.mongobackend.model.User;
import com.bit.mongobackend.repository.UserRepository;
import com.bit.mongobackend.service.UserService;
import com.bit.mongobackend.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
@AllArgsConstructor
public class UserController {
    private final UserRepository USER_REPOSITORY;
    private final JwtUtil JWT_UTIL;
    private final PasswordEncoder PASSWORD_ENCODER;
    private final UserService USER_SERVICE;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody User user){
        if(Strings.isEmpty(user.getUsername()) || Strings.isEmpty(user.getPassword())|| Strings.isEmpty(user.getNickname())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("모두 입력해주세요!");
        }

        if(USER_REPOSITORY.findByUsername(user.getUsername()) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 아이디입니다.");

        }

        if(USER_REPOSITORY.findByNickname(user.getNickname()) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 닉네임입니다.");

        }
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        USER_SERVICE.regisetr(user);

        return ResponseEntity.ok("회원가입 완료!");
    }
    
    @PostMapping("auth")
    public ResponseEntity<?> auth(@RequestBody User user){
        User origin = USER_REPOSITORY.findByUsername(user.getUsername());
        
        if(origin == null || !PASSWORD_ENCODER.matches(user.getPassword(),origin.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("실패");
        }

        String token=JWT_UTIL.createToken(origin.getUsername());

        return ResponseEntity.ok(token);
    }
}
