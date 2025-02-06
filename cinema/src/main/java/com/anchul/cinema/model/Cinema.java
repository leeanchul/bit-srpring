package com.anchul.cinema.model;

import lombok.Data;

import java.sql.Time;

@Data
public class Cinema {
    private int id;
    private int movieId;
    private int roomNum;
    private Time showTime;
    private String area;
}
