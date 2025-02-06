package com.anchul.cinema.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Scope {
    private int id;
    private int movieId;
    private int userId;
    private double score;
    private String review;
    private String userRole;
    private Date entryDate;
    private String nickname;
    private double maxAvg;
    private int count;
}
