package com.example.moviebackend.controller;

import com.example.moviebackend.model.TokenDTO;
import com.example.moviebackend.model.UserDTO;
import com.example.moviebackend.service.AuthService;
import com.example.moviebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @PostMapping("auth")
    public Object auth(@RequestBody UserDTO userDTO){
        Map<String,Object> resultMap=new HashMap<>();
        TokenDTO tokenDTO=authService.auth(userDTO);
        resultMap.put("result",tokenDTO);

        return resultMap;
    }
    @PostMapping("register")
    public Object register(@RequestBody UserDTO userDTO){
        Map<String,Object> resultMap=new HashMap<>();

        if(!userService.validateUsername(userDTO)){
            resultMap.put("result","fail");
            resultMap.put("message","중복된 아이디는 사용할 수 없습니다.");
        } else if(!userService.validateNickname(userDTO)){
            resultMap.put("result","fail");
            resultMap.put("message","중복된 닉네임은 사용할 수 없습니다.");
        } else{
            userService.register(userDTO);
            resultMap.put("result","success");
            resultMap.put("message","가입 완료");
        }

        return resultMap;
    }
}
