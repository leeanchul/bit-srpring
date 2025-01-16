package com.bit.restful_api.controller;


import com.bit.restful_api.model.BoardDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/board/")
public class BoardController {


    @GetMapping("showAll")
    public Map<String,Object> showAll() {
        ArrayList<BoardDTO> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setId(i);
            boardDTO.setTitle("제목: " + i);
            boardDTO.setContent("내용: " + i);
            list.add(boardDTO);
        }

        Map<String, Object> resultMap = new HashMap<>();

        // 요청에 대한 성공/실패 를 응답에 포함
        resultMap.put("result", "success");
        // 위의 리스트를 추가
        resultMap.put("list", list);

        return resultMap;
    }
}
