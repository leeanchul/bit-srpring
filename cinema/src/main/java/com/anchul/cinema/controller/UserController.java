package com.anchul.cinema.controller;

import com.anchul.cinema.jwt.JwtUtil;
import com.anchul.cinema.model.User;
import com.anchul.cinema.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
public class UserController {
    private final JwtUtil JWT_UTIL;
    private final PasswordEncoder PASSWORD_ENCODER;
    private final UserService USER_SERVICE;

    public UserController(JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UserService userService) {
        JWT_UTIL = jwtUtil;
        PASSWORD_ENCODER = passwordEncoder;
        USER_SERVICE = userService;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody User user){
        user.setRole("ROLE_USER");
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));

        USER_SERVICE.register(user);
        return ResponseEntity.ok("회원가입 완료!");
    }

    @PostMapping("auth")
    public ResponseEntity<?> auth(@RequestBody User user){
        User origin=USER_SERVICE.findByUsername(user.getUsername());

        System.out.println(user.getUsername());
        if (origin == null || !PASSWORD_ENCODER.matches(user.getPassword(), origin.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("실패");
        }
        String token = JWT_UTIL.createToken(origin.getUsername());

       return ResponseEntity.ok(token);
    }
}
