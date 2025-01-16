package com.movie.review.controller;

import com.movie.review.model.ReviewDTO;
import com.movie.review.service.ReviewService;
import com.movie.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/review/")
public class ReviewController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/insert/{movieId}")
    public String insert(@PathVariable int movieId, String content){
        // 현재 로그인한 사용자 username을 가져오기
        String user= SecurityContextHolder.getContext().getAuthentication().getName();
        // 로그인 사용자 정보 가져오기
        int userId=userService.loadByUsername(user).getId();
        String role=userService.loadByUsername(user).getRole();
        // 리뷰 정보 세팅해주기
        ReviewDTO reviewDTO=new ReviewDTO();
        reviewDTO.setUserId(userId);
        reviewDTO.setMovieId(movieId);
        reviewDTO.setContent(content);

        System.out.println(role);
        if(reviewService.validateUserid(reviewDTO) && role.equals("ROLE_REVIEWER")){
            System.out.println("리뷰 등록");
            reviewService.insert(reviewDTO);
        }else{
            System.out.println("이미 등록함");
        }

        System.out.println(reviewDTO);
        return "redirect:/movie/movieOne/"+movieId;
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id){
        String user= SecurityContextHolder.getContext().getAuthentication().getName();
        int userId=userService.loadByUsername(user).getId();
        int pageId=reviewService.reviewOne(id).getMovieId();
        if(userId ==reviewService.reviewOne(id).getUserId() ){
            System.out.println("현재로그인한 사용자 id와 글쓴사람 아이디가같음");
            reviewService.delete(id);
        }

        return "redirect:/movie/movieOne/"+pageId;
    }
}
