package com.bit.board_backend.controller;

import com.bit.board_backend.model.UserDTO;
import com.bit.board_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user/")
@AllArgsConstructor
// 생성자 주입
public class UserController {
    private final UserService USER_SERVICE;
    private final UserService userService;

    // 로그인 요청에 대한 결과 처리
    @PostMapping("auth")
    public Object auth(@RequestBody UserDTO userDTO){
        Map<String,Object> resultMap=new HashMap<>();
        UserDTO result = USER_SERVICE.auth(userDTO);

        if(result != null){
            resultMap.put("result","성공 !");
        } else{
            resultMap.put("result","실패");
            resultMap.put("message","확인좀해라~");
        }

        return resultMap;
    }
    // 회원 가입 요청에 대한 결과 처리
    @PostMapping("register")
    public Object register(@RequestBody UserDTO userDTO){
        Map<String,Object> resultMap=new HashMap<>();

        if(!userService.validateUsername(userDTO)){
            resultMap.put("result","fail");
            resultMap.put("message","Duplicated Username");
        } else if(!userService.validateNickname(userDTO)){
            resultMap.put("result","fail");
            resultMap.put("message","Duplicated Nickname");
        } else{
            resultMap.put("result","가입 완료!!");
        }

        return resultMap;
    }

}
