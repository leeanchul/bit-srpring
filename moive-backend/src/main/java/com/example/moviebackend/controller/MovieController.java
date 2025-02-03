package com.example.moviebackend.controller;

import com.example.moviebackend.model.MovieDTO;
import com.example.moviebackend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movie/")
@CrossOrigin("http://localhost:3000")
public class MovieController {
    @Autowired
    private MovieService movieService;

//    @GetMapping("movieAll/1")
//    public Object movieAll(){
//        Map<String,Object> resultMap=new HashMap<>();
//        List<MovieDTO> list=movieService.movieAll();
//
//        resultMap.put("result","success");
//        resultMap.put("list",list);
//        return resultMap;
//    }

    @GetMapping("movieAll/{page}")
    public Object movieAll(@PathVariable int page){
        Map<String, Object> resultMap = new HashMap<>();
        List<MovieDTO> list=movieService.selectByPage(page);
        if (list.isEmpty()) {
            resultMap.put("result", "fail");
            resultMap.put("message", " 유효하지않은 페이지");
        }else{
            resultMap.put("result", "success");
            resultMap.put("list",list);

            int maxPage = movieService.maxPage();

            int startPage = page - 2;
            int endPage = page + 2;

            if (maxPage <= 5) {
                startPage = 1;
                endPage = maxPage;
            } else if (page <= 3) {
                startPage = 1;
                endPage = 5;
            } else if (page >= maxPage - 2) {
                startPage = maxPage - 4;
                endPage = maxPage;
            }
            resultMap.put("maxPage", maxPage);
            resultMap.put("startPage", startPage);
            resultMap.put("endPage", endPage);
            resultMap.put("currentPage", page);

        }

        return resultMap;
    }

    @PostMapping("insert")
    public Object insert(@RequestBody MovieDTO movieDTO){
        Map<String,Object> resultMap=new HashMap<>();

        try {
            movieService.insert(movieDTO);
            resultMap.put("result", "success");
            resultMap.put("boardDTO", movieDTO);
        } catch (Exception e) {
            resultMap.put("result", "fail");
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }
    @GetMapping("movieOne/{id}")
    public Object movieOne(@PathVariable int id){
        Map<String,Object> resultMap=new HashMap<>();
        MovieDTO movieDTO=movieService.movieOne(id);
        if(movieDTO!=null){
            resultMap.put("result","success");
            resultMap.put("movieDTO",movieDTO);
        }else{
            resultMap.put("result","fail");
        }
        return resultMap;
    }
    @GetMapping("delete/{id}")
    public Object delete(@PathVariable int id){
        Map<String,Object> resultMap=new HashMap<>();
        try {
            movieService.delete(id);
            resultMap.put("result", "success");
        } catch (Exception e) {
            resultMap.put("result", "fail");
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }
    @PostMapping("update")
    public Object update(@RequestBody MovieDTO movieDTO){
        Map<String,Object> resultMap=new HashMap<>();
        try {
            movieService.update(movieDTO);
            resultMap.put("result", "success");
        } catch (Exception e) {
            resultMap.put("result", "fail");
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }
}
