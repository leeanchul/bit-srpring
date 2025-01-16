package com.example.ACboard.controller;

import com.example.ACboard.model.TokenDTO;
import com.example.ACboard.model.UserDTO;
import com.example.ACboard.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("test")
    public String test(Model model,HttpSession session){

        model.addAttribute("test", session.getAttribute("jwt"));

        return "user/test";
    }

    @GetMapping("register")
    public String register(){
        return "user/register";
    }

    @GetMapping("loginpage")
    public String login(){
        System.out.println("로그인");
        return "user/loginpage";
    }

    @PostMapping("login")
    public String login2(UserDTO userDTO, HttpSession session){
        TokenDTO tokenDTO=authService.auth(userDTO);
        System.out.println("로그인post ");
        String jwt=tokenDTO.getType()+" "+tokenDTO.getValue();
        session.setAttribute("jwt",jwt);

        System.out.println(jwt);
        return "redirect:/test";
    }

}
