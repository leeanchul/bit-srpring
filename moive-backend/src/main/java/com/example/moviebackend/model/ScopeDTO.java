package com.example.moviebackend.model;

import lombok.Data;

@Data
public class ScopeDTO {
    private int id;
    private int movieId;
    private int userId;
    private double score;
    private String userRole;
}
