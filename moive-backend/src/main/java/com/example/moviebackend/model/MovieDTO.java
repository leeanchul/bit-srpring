package com.example.moviebackend.model;

import lombok.Data;

import java.util.Date;

@Data
public class MovieDTO {
    private int id;
    private String title;
    private String director;
    private String content;
    private Date entryDate;
    private Date modifyDate;
    private String author;
    private double scope;
    private String fileName;
    private String filePath;
}
