package com.bit.board_backend.model;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String username;
    private String nickname;
    private String password;
}
