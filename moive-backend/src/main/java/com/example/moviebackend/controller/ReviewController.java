package com.example.moviebackend.controller;

import com.example.moviebackend.model.ReviewDTO;
import com.example.moviebackend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review/")
@CrossOrigin("http://localhost:3000")

public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("test")
    public String test(){
        return "test";
    }

    @GetMapping("reviewAll/{movieId}")
    public Object reviewAll(@PathVariable int movieId){
        Map<String,Object> resultMap=new HashMap<>();
        List<ReviewDTO> list=reviewService.reviewAll(movieId);

        if(list != null){
            resultMap.put("result","success");
            resultMap.put("list",list);
        }else{
            resultMap.put("result","fail");
        }

        return resultMap;
    }

    @PostMapping("insert")
    public Object inset(@RequestBody ReviewDTO reviewDTO){
        Map<String,Object> resultMap=new HashMap<>();

        if(reviewService.validateUserid(reviewDTO)){
            reviewService.insert(reviewDTO);
            resultMap.put("result", "success");
            resultMap.put("reviewDTO", reviewDTO);
        }else{
            resultMap.put("result", "fail");
            resultMap.put("message", "이미 등록함");
        }
        
        return resultMap;
    }

    @PostMapping("update")
    public Object update(@RequestBody ReviewDTO reviewDTO){
        Map<String,Object> resultMap=new HashMap<>();

        try{
            reviewService.update(reviewDTO);
            resultMap.put("result", "success");
            resultMap.put("reviewDTO", reviewDTO);
        }catch (Exception e){
            resultMap.put("result", "fail");
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }
    @GetMapping("delete/{id}")
    public Object delete(@PathVariable int id){
        Map<String,Object> resultMap=new HashMap<>();
        try {
            reviewService.delete(id);
            resultMap.put("result", "success");
        } catch (Exception e) {
            resultMap.put("result", "fail");
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }
}
