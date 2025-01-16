package com.example.ACboard.controller;

import com.example.ACboard.model.TokenDTO;
import com.example.ACboard.model.UserDTO;
import com.example.ACboard.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("test")
    public String test(){
        return "user/test";
    }

    @GetMapping("register")
    public String register(){
        return "user/register";
    }

    @GetMapping("login")
    public String login(){
        System.out.println("로그인");
        return "user/login";
    }

    @PostMapping("login")
    public String login2(UserDTO userDTO, HttpSession session){
        TokenDTO tokenDTO=authService.auth(userDTO);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("result",tokenDTO);
        System.out.println("로그인post ");
        session.setAttribute("jwt",resultMap);


        return "redirect:/test";
    }

}
