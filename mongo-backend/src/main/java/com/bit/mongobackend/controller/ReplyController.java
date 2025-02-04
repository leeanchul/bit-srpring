package com.bit.mongobackend.controller;

import com.bit.mongobackend.model.Board;
import com.bit.mongobackend.model.Reply;
import com.bit.mongobackend.model.User;
import com.bit.mongobackend.repository.ReplyRepository;
import com.bit.mongobackend.service.ReplyService;
import com.bit.mongobackend.service.UserService;
import com.bit.mongobackend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reply/")
@AllArgsConstructor
public class ReplyController {
    private final ReplyRepository REPLY;
    private final JwtUtil JWT_UTIL;
    private final UserService USER_SERVICE;
    private final ReplyService REPLY_SERVICE;
    @GetMapping("showAll/{boardId}")
    public ResponseEntity<?> showAll(@PathVariable int boardId){

        List<Reply> temp =REPLY_SERVICE.findReplyBy(boardId);
        System.out.println(temp);
        return ResponseEntity.ok(temp);
    }

    @PostMapping("write")
    public ResponseEntity<?>write(@RequestBody Reply reply, HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 존재 XX");
        }
        String username = JWT_UTIL.validateToken(authHeader);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 존재 XX");
        }
        User login = USER_SERVICE.findByUsername(username);

        reply.setWriterId(login.getId());
        reply.setEntryDate(new Date());
        reply.setModifyDate(new Date());
        Reply r=REPLY_SERVICE.insert(reply);
        return ResponseEntity.ok(r);
    }

    @GetMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        REPLY_SERVICE.delete(id);
        return ResponseEntity.ok("success");
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody Reply reply){
        Reply r=REPLY_SERVICE.update(reply);

        return ResponseEntity.ok(r);
    }
}
