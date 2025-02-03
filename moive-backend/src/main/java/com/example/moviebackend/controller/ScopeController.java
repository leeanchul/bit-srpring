package com.example.moviebackend.controller;

import com.example.moviebackend.model.ScopeDTO;
import com.example.moviebackend.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/scope/")
@CrossOrigin("http://localhost:3000")
public class ScopeController {
    @Autowired
    private ScopeService scopeService;

    @GetMapping("scopeAll/{movieId}")
    public Object scopeAll(@PathVariable int movieId){
        Map<String,Object> resultMap=new HashMap<>();
        double avg=scopeService.scopeAll(movieId);
        int count=scopeService.count(movieId);
        resultMap.put("result","success");
        resultMap.put("avg",avg);
        resultMap.put("count",count);
        return resultMap;
    }
    @PostMapping("insert")
    public Object insert(@RequestBody ScopeDTO scopeDTO){
        Map<String,Object> resultMap=new HashMap<>();

        if(scopeService.validate(scopeDTO)){
            scopeService.insert(scopeDTO);
            resultMap.put("result","success");
        }else{
            resultMap.put("result","fail");
        }
        return resultMap;
    }
    @PostMapping("scopeRole")
    public Object scopeRole(@RequestBody ScopeDTO scopeDTO){
        Map<String,Object> resultMap=new HashMap<>();
        double avg=scopeService.scopeRole(scopeDTO);
        int count=scopeService.count(scopeDTO);
        resultMap.put("result","success");
        resultMap.put("avg",avg);
        resultMap.put("count",count);

        return resultMap;
    }
}
