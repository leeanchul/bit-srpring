package com.bit.board_front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/")
public class BoardController {
    @GetMapping("showAll")
    public String mveToFirst(){
        return "redirect:/board/showAll/1";
    }

    @GetMapping("showAll/{page}")
    public String showAll(){
        return "board/showAll";
    }

    @GetMapping("showOne/{id}")
    public String showOne(@PathVariable String id){
        return "/board/showOne";
    }
    @GetMapping("update/{id}")
    public String update(){
        return "board/update";
    }

    @GetMapping("delete/{id}")
    public String delete(){
        return "redirect:/board/showAll/1";
    }

    @GetMapping("insert")
    public String insert(){
        return "board/insert";
    }

    @GetMapping("write")
    public String write(){
        return "board/write";
    }
}
