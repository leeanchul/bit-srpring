package com.anchul.cinema.controller;

import com.anchul.cinema.jwt.JwtUtil;
import com.anchul.cinema.model.Scope;
import com.anchul.cinema.model.User;
import com.anchul.cinema.service.ScopeService;
import com.anchul.cinema.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/scope/")
public class ScopeController {
    private final ScopeService SCOPE_SERVICE;
    private final JwtUtil JWT_UTIL;
    private final UserService USER_SERVICE;
    public ScopeController(ScopeService scopeService, JwtUtil jwtUtil, UserService userService) {
        SCOPE_SERVICE = scopeService;
        JWT_UTIL = jwtUtil;
        USER_SERVICE = userService;
    }

    @PostMapping("scoreInsert")
    public ResponseEntity<?> scoreInsert(@RequestBody Scope scope, HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 없음");
        }
        String username = JWT_UTIL.validateToken(authHeader);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 존재 XX");
        }
        User login = USER_SERVICE.findByUsername(username);
        scope.setNickname(login.getNickname());
        scope.setUserId(login.getId());
        scope.setUserRole(login.getRole());
        scope.setEntryDate(new Date());

        Scope check=SCOPE_SERVICE.validate(scope);
        if(check==null){
            SCOPE_SERVICE.scoreInsert(scope);
            return ResponseEntity.ok("success");
        } else if(check.getScore()==0 && check.getReview()!=null) {
            // 업데이트 해준다.
            scope.setId(check.getId());
            SCOPE_SERVICE.scoreUpdate(scope);
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("실패");
    }

    @PostMapping("scopeAvg")
    public ResponseEntity<?> scopeAvg(@RequestBody Scope scope, HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 없음");
        }
        String username = JWT_UTIL.validateToken(authHeader);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 존재 XX");
        }
        User login = USER_SERVICE.findByUsername(username);
        scope.setUserId(login.getId());
        Scope check=SCOPE_SERVICE.validate(scope);
        if(check==null || check.getScore()!=0){
            Scope result=SCOPE_SERVICE.scopeAvg(scope);
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("점수 없음");
    }

    @GetMapping("reviewAll/{movieId}")
    public ResponseEntity<?> reviewAll(@PathVariable int movieId, HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 없음");
        }
        String username = JWT_UTIL.validateToken(authHeader);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 존재 XX");
        }
        User login = USER_SERVICE.findByUsername(username);
        Scope scope=new Scope();
        scope.setUserId(login.getId());
        scope.setMovieId(movieId);
        Scope check=SCOPE_SERVICE.validate(scope);

        if(check!=null && check.getReview()!=null){
            List<Scope> list=SCOPE_SERVICE.reviewAll(movieId);
            return ResponseEntity.ok(list);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("XX");
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

        Scope scope=SCOPE_SERVICE.ScopeOne(id);
        if (login.getId() == scope.getUserId()) {
            SCOPE_SERVICE.deleteById(id);
            return ResponseEntity.ok("삭제 되었습니다.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("본인이 작성한 글이 아닙니다.");
    }

    @PostMapping("reviewInsert")
    public ResponseEntity<?> reviewInsert(@RequestBody Scope scope,HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 없음");
        }
        String username = JWT_UTIL.validateToken(authHeader);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 존재 XX");
        }
        // 별점을 남기지 않았다면 새로 추가
        User login = USER_SERVICE.findByUsername(username);
        scope.setNickname(login.getNickname());
        scope.setUserId(login.getId());
        scope.setUserRole(login.getRole());
        scope.setEntryDate(new Date());
        Scope check=SCOPE_SERVICE.validate(scope);
        if(check==null){
            SCOPE_SERVICE.reviewInsert(scope);
            return ResponseEntity.ok("확인");
        }else if(check.getScore()!=0 && check.getReview()==null){
            // update 를 한다.
            scope.setId(check.getId());
            SCOPE_SERVICE.reviewUpdate(scope);
            return ResponseEntity.ok("확인");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("실패");
    }
}
