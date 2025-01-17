package com.study.ACboard.controller;

import com.study.ACboard.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {

    @GetMapping("login")
    public String login(){
        return "user/login";
    }
    @GetMapping("register")
    public String register(){

        return "user/register";
    }
    @PostMapping("register")
    public String register(UserDTO userDTO){
        System.out.println(userDTO);

        return "redirect:/";
    }
}
