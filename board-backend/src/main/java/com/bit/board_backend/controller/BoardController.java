package com.bit.board_backend.controller;

import com.bit.board_backend.model.BoardDTO;
import com.bit.board_backend.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/board/")
@CrossOrigin("http://localhost:1841")
public class BoardController {
    private final BoardService BOARD_SERVICE;
    private final String LIST_FORMAATTER= "yy-MM-dd HH:mm:ss";
    private final String InDIV_FORMATTER="yyyy년 MM월 dd일 HH시 mm분 ss초";

    @GetMapping("showAll")
    public Object showAll(){
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("list",BOARD_SERVICE.selectAll());
        resultMap.put("total",BOARD_SERVICE.countAll());

        return resultMap;
    }


    @GetMapping("showAll/{page}")
    public Object showAll(@PathVariable String page) {
        Map<String, Object> resultMap = new HashMap<>();


        int pageNo = 0;
        try {
            pageNo = Integer.parseInt(page);
        } catch (Exception e) {
            resultMap.put("result", "fail");
            resultMap.put("message", "올바르지 않은 페이지 번호");

            return resultMap;
        }


        List<BoardDTO> list = BOARD_SERVICE.selectByPage(pageNo);

        if (list.isEmpty()) {
            resultMap.put("result", "fail");
            resultMap.put("message", " 유효하지않은 페이지");
        } else {
            resultMap.put("result", "success");

            SimpleDateFormat formatter=new SimpleDateFormat();

            for(BoardDTO b:list){
                b.setFormattedEntryDate(formatter.format(b.getEntryDate()));
                b.setFormattedModifyDate(formatter.format(b.getModifyDate()));
            }


            resultMap.put("list", list);


            int maxPage = BOARD_SERVICE.selectMaxPage();

            int startPage = pageNo - 2;
            int endPage = pageNo + 2;

            if(maxPage <= 5){
                startPage =1;
                endPage =maxPage;
            } else if (pageNo <= 3) {
                startPage = 1;
                endPage = 5;
            } else if (pageNo >= maxPage - 2) {
                startPage = maxPage - 4;
                endPage = maxPage;
            }
            resultMap.put("maxPage", maxPage);
            resultMap.put("startPage", startPage);
            resultMap.put("endPage", endPage);
            resultMap.put("currentPage", pageNo);
        }
        return resultMap;
    }

    @GetMapping("showOne/{id}")
    public Object showOne(@PathVariable String id) {
        Map<String, Object> resultMap = new HashMap<>();
        BoardDTO boardDTO = BOARD_SERVICE.selectOne(id);
        if (!id.matches("^\\d+$") || boardDTO == null) {
            resultMap.put("result", "fail");
            resultMap.put("message", "올바르지 않은 글 번호입니다.");
        } else {
            resultMap.put("result", "success");

            SimpleDateFormat sdf=new SimpleDateFormat(InDIV_FORMATTER);
            boardDTO.setFormattedEntryDate(sdf.format(boardDTO.getEntryDate()));
            boardDTO.setFormattedModifyDate(sdf.format(boardDTO.getModifyDate()));

            resultMap.put("boardDTO", boardDTO);
        }

        return resultMap;
    }

    @PostMapping("write")
    public Object write(@RequestBody BoardDTO boardDTO) {
        System.out.println("인설트"+boardDTO);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            BOARD_SERVICE.insert(boardDTO);
            resultMap.put("result", "success");
            resultMap.put("boardDTO", boardDTO);
        } catch (Exception e) {
            resultMap.put("result", "fail");
            resultMap.put("message", e.getMessage());
        }


        return resultMap;
    }

    @PostMapping("update")
    public Object update(@RequestBody BoardDTO boardDTO) {
        System.out.println(boardDTO);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            BOARD_SERVICE.update(boardDTO);
            resultMap.put("result", "success");
            resultMap.put("boardDTO", boardDTO);
        } catch (Exception e) {
            resultMap.put("result", "fail");
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    @GetMapping("delete/{id}")
    public Object delete(@PathVariable String id) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            BOARD_SERVICE.delete(id);
            resultMap.put("result", "success");
        } catch (Exception e) {
            resultMap.put("result", "fail");
            resultMap.put("message", "삭제 실패");
        }

        return resultMap;
    }
}
