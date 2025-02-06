package com.anchul.cinema.model;

import lombok.Data;

import java.util.List;

@Data
public class MoviePage {
    private List<Movie> content;
    private int currentPage;
    private int endPage;
    private int startPage;
    private int maxPage;
}
