package com.bit.mongobackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Data
public class User {
    @Id
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String role;
}
