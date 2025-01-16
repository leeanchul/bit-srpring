package com.movie.review.controller;

import com.movie.review.model.UserDTO;
import com.movie.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("join")
    public String join(){
        return "user/join";
    }

    @PostMapping("join")
    public String join(UserDTO userDTO){
        if(!userService.validateUsername(userDTO.getUsername())){
            System.out.println("중복된 아이디입니다.");
            return "user/join";
        }
        userService.insert(userDTO);
        return "redirect:/";
    }
}
