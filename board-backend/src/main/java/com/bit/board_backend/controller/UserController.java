package com.bit.board_backend.controller;

import com.bit.board_backend.model.UserDTO;
import com.bit.board_backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user/")
@AllArgsConstructor
@CrossOrigin("http://localhost:8081")
// 생성자 주입
public class UserController {
    private final UserService USER_SERVICE;

    // 로그인 요청에 대한 결과 처리
    @PostMapping("auth")
    public Object auth(@RequestBody UserDTO userDTO){
        Map<String,Object> resultMap=new HashMap<>();
        UserDTO result = USER_SERVICE.auth(userDTO);

        if(result != null){
            resultMap.put("result","success!");
            resultMap.put("login",result);
        } else{
            resultMap.put("result","fail");
            resultMap.put("message","확인좀해라~");
        }

        return resultMap;
    }
    // 회원 가입 요청에 대한 결과 처리
    @PostMapping("register")
    public Object register(@RequestBody UserDTO userDTO){
        Map<String,Object> resultMap=new HashMap<>();

        if(!USER_SERVICE.validateUsername(userDTO)){
            resultMap.put("result","fail");
            resultMap.put("message","중복된 아이디는 사용할 수 없습니다.");
        } else if(!USER_SERVICE.validateNickname(userDTO)){
            resultMap.put("result","fail");
            resultMap.put("message","중복된 닉네임은 사용할 수 없습니다.");
        } else{
            USER_SERVICE.register(userDTO);
            resultMap.put("result","가입 완료!!");
        }

        return resultMap;
    }


}
