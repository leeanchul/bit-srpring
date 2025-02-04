package com.bit.mongobackend.model;


import lombok.Data;

import java.util.Date;

@Data
public class Board {
    private int id;
    private String title;
    private String content;
    private int writerId;
    private String writerNickname;
    private Date entryDate;
    private Date modifyDate;
}
