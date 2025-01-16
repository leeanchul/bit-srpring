package com.movie.review.controller;

import com.movie.review.model.MovieDTO;
import com.movie.review.model.ReviewDTO;
import com.movie.review.service.MovieService;
import com.movie.review.service.ReviewService;
import com.movie.review.service.ScopeService;
import com.movie.review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/movie/")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;


    @GetMapping("movieAll")
    public String movieAll(Model model){
        List<MovieDTO> list=movieService.movieAll();
        String user= SecurityContextHolder.getContext().getAuthentication().getName();
        String role=userService.loadByUsername(user).getRole();
        model.addAttribute("list",list);
        model.addAttribute("role",role);
        return "movie/movieAll";
    }

    @GetMapping("movieOne/{id}")
    public String movieOne(@PathVariable int id, Model model){
        String user= SecurityContextHolder.getContext().getAuthentication().getName();
        MovieDTO movieDTO=movieService.movieOne(id);
        double scoreAll=scopeService.scoreAll(id);
        List<ReviewDTO> reviewList=reviewService.reviewAll(id);
        String role=userService.loadByUsername(user).getRole();

        model.addAttribute("movieDTO",movieDTO);
        model.addAttribute("scoreAll",scoreAll);
        model.addAttribute("reviewList",reviewList);
        model.addAttribute("user",user);
        model.addAttribute("role",role);
        return "movie/movieOne";
    }

    @GetMapping("insert")
    public String insert(){
        String user= SecurityContextHolder.getContext().getAuthentication().getName();
        String role=userService.loadByUsername(user).getRole();
        if(role.equals("ADMIN")){
            return "movie/insert";
        }
        return "redirect:/movie/movieAll";
    }

    @PostMapping("insert")
    public String insert(MovieDTO movieDTO){
        String user= SecurityContextHolder.getContext().getAuthentication().getName();
        String role=userService.loadByUsername(user).getRole();
        if(role.equals("ADMIN")){
            movieService.insert(movieDTO);
        }

        return "redirect:/movie/movieAll";
    }

    @GetMapping("update/{id}")
    public String update(@PathVariable int id,Model model){
        String user= SecurityContextHolder.getContext().getAuthentication().getName();
        String role=userService.loadByUsername(user).getRole();
        if(role.equals("ADMIN")){
            MovieDTO movieDTO=movieService.movieOne(id);
            model.addAttribute("movieDTO",movieDTO);
            return "movie/update";
        }

        return "redirect:/movie/movieOne/"+id;
    }

    @PostMapping("update/{id}")
    public String update(@PathVariable int id,MovieDTO movieDTO){
        String user= SecurityContextHolder.getContext().getAuthentication().getName();
        String role=userService.loadByUsername(user).getRole();

        if(role.equals("ADMIN")){
            movieService.update(movieDTO);
        }

        return "redirect:/movie/movieOne/"+id;
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id){
        String user= SecurityContextHolder.getContext().getAuthentication().getName();
        String role=userService.loadByUsername(user).getRole();

        if(role.equals("ADMIN")){
            movieService.delete(id);
            return "redirect:/movie/movieAll";
        }

        System.out.println(id+"삭제");
        return "redirect:/movie/movieOne/"+id;
    }
}
