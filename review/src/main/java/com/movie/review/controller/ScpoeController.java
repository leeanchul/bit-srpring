package com.movie.review.controller;

import com.movie.review.model.ScopeDTO;
import com.movie.review.service.ScopeService;
import com.movie.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scope/")
public class ScpoeController {
    @Autowired
    private UserService userService;

    @Autowired
    private ScopeService scopeService;

    
    @PostMapping("insert/{movieId}")
    public String insert(@PathVariable int movieId, ScopeDTO scopeDTO){
        // 현재 로그인한 사용자 username을 가져오기
        String user= SecurityContextHolder.getContext().getAuthentication().getName();
        // 로그인 사용자에 id 값을 가져오기
        int id=userService.loadByUsername(user).getId();
        String role=userService.loadByUsername(user).getRole();
        scopeDTO.setUserId(id);
        scopeDTO.setUserRole(role);
        System.out.println(scopeDTO);

        if(scopeService.validateUserid(scopeDTO)){
            System.out.println("점수 등록");
            scopeService.insert(scopeDTO);
        }else{
            System.out.println("이미 점수 했음");
        }

        return "redirect:/movie/movieOne/"+movieId;
    }

}
