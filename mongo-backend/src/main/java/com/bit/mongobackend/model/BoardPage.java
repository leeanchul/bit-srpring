package com.bit.mongobackend.model;

import lombok.Data;

import java.util.List;

@Data
public class BoardPage {
    private List<Board> content;
    private int currentPage;
    private int endPage;
    private int startPage;
    private int maxPage;
}
