package com.bit.mongobackend.controller;

import com.bit.mongobackend.model.Board;
import com.bit.mongobackend.model.BoardPage;
import com.bit.mongobackend.model.User;
import com.bit.mongobackend.repository.BoardRepository;
import com.bit.mongobackend.service.BoardService;
import com.bit.mongobackend.service.UserService;
import com.bit.mongobackend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/board/")
@AllArgsConstructor
public class BoardController {

    private final BoardService BOARD_SERVICE;
    private final JwtUtil JWT_UTIL;
    private final UserService USER_SERVICE;
    private final int PAGE_SIZE = 15;

    @GetMapping("check")
    public ResponseEntity<?> check(HttpServletRequest request) {

        for (int i = 1; i <= 100; i++) {
            Board b = new Board();
            b.setTitle("제목" + i);
            b.setContent("내용" + i);
            b.setWriterId(1);
            b.setEntryDate(new Date());
            b.setModifyDate(new Date());

            BOARD_SERVICE.insert(b);
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping("showAll/{pageNo}")
    public ResponseEntity<?> showAll(@PathVariable int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Board> temp = BOARD_SERVICE.findAllWithUser(pageable);
        BoardPage boardPage = new BoardPage();
        boardPage.setContent(temp.getContent());
        boardPage.setCurrentPage(pageNo);

        // Math.ceil : double 값을 올림처리한 정수 값
        // Math.floor: double 값을 내림처리한 정수 값
        // Math.round: double 값을 반올림처리한 정수 값

        int maxPage = (int) Math.ceil((double) BOARD_SERVICE.countTotal() / PAGE_SIZE);
        boardPage.setMaxPage(maxPage);

        int startPage = 1;
        int endPage = 5;
        if (maxPage < 5) {
            endPage = maxPage;
        } else if (pageNo > maxPage - 3) {
            startPage = maxPage - 4;
            endPage = maxPage;
        } else if (pageNo <= 3) {
            startPage = 1;
            endPage = 5;
        } else {
            startPage = pageNo - 2;
            endPage = pageNo + 2;
        }
        boardPage.setStartPage(startPage);
        boardPage.setEndPage(endPage);

        return ResponseEntity.ok(boardPage);
    }

    @GetMapping("showOne/{pageNo}")
    public ResponseEntity<?> showOne(@PathVariable int pageNo) {
        Board b=BOARD_SERVICE.selectOne(pageNo);
        return ResponseEntity.ok(b);
    }

    @PostMapping("write")
    public ResponseEntity<?> write(@RequestBody Board board, HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 존재 XX");
        }
        String username = JWT_UTIL.validateToken(authHeader);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 존재 XX");
        }


        User login = USER_SERVICE.findByUsername(username);

        board.setWriterId(login.getId());
        board.setEntryDate(new Date());
        board.setModifyDate(new Date());

        Board b = BOARD_SERVICE.insert(board);
        return ResponseEntity.ok(b);
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody Board board) {
        Board b = BOARD_SERVICE.update(board);

        return ResponseEntity.ok(b);
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        System.out.println("호출");
        BOARD_SERVICE.delete(id);
        return ResponseEntity.ok("success");
    }
}
