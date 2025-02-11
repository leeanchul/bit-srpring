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
        scope.setUserId(login.getId());
        scope.setUserRole(login.getRole());
        scope.setEntryDate(new Date());

        if (scope.getUserRole()!=null && SCOPE_SERVICE.validate(scope)) {

            SCOPE_SERVICE.scoreInsert(scope);
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
        Scope result = SCOPE_SERVICE.scopeAvg(scope);
        return ResponseEntity.ok(result);
    }

}
