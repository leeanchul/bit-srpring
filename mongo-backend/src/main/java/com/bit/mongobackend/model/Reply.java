package com.bit.mongobackend.model;

import lombok.Data;

import java.util.Date;

@Data
public class Reply {
    private int id;
    private String content;
    private int boardId;
    private int writerId;
    private String writerNickname;
    private Date entryDate;
    private Date modifyDate;
}
