package com.anchul.cinema.controller;

import com.anchul.cinema.jwt.JwtUtil;
import com.anchul.cinema.model.Cinema;
import com.anchul.cinema.model.Page;
import com.anchul.cinema.model.User;
import com.anchul.cinema.service.CinemaService;
import com.anchul.cinema.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinema/")
public class CinemaController {
    private final JwtUtil JWT_UTIL;
    private final UserService USER_SERVICE;
    private final CinemaService CINEMA_SERVICE;
    private final int PAGE_SIZE = 5;
    public CinemaController(JwtUtil jwtUtil, UserService userService, CinemaService cinemaService) {
        JWT_UTIL = jwtUtil;
        USER_SERVICE = userService;
        CINEMA_SERVICE = cinemaService;
    }

    @GetMapping("cinemaAll")
    public ResponseEntity<?> cinemaAll(){
        List<Cinema> list = CINEMA_SERVICE.cinemaAll();

        return ResponseEntity.ok(list);
    }

    @GetMapping("cinemaAll/{pageNo}")
    public ResponseEntity<?> cinemaAll(@PathVariable int pageNo){

        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Cinema> temp=CINEMA_SERVICE.findAllWithUser(pageable);
        Page page =new Page();
        page.setContent(temp.getContent());
        page.setCurrentPage(pageNo);

        int maxPage=  (int) Math.ceil((double) CINEMA_SERVICE.countTotal() / PAGE_SIZE);
        page.setMaxPage(maxPage);
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
        page.setStartPage(startPage);
        page.setEndPage(endPage);

        return ResponseEntity.ok(page);
    }
    @PostMapping("insert")
    public ResponseEntity<?> insert(@RequestBody Cinema cinema){
       CINEMA_SERVICE.cinemaInsert(cinema);
        return ResponseEntity.ok("영화관 추가 완료!");
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id, HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 없음");
        }
        String username = JWT_UTIL.validateToken(authHeader);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 존재 XX");
        }
        User login = USER_SERVICE.findByUsername(username);

        if(login.getRole().equals("ROLE_MASTER")){
            CINEMA_SERVICE.deleteById(id);
            return ResponseEntity.ok("삭제");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("관리자가 아닙니다.");
    }
}
