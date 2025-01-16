package com.movie.review.model;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewDTO {
    private int id;
    private int userId;
    private int movieId;
    private String content;
    private Date entryDate;
    private Date modifyDate;
    private String nickname;

}