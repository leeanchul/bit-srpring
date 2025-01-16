package com.movie.review.model;

import lombok.Data;

@Data
public class ScopeDTO {
    private int id;
    private int movieId;
    private int userId;
    private int score;
    private String userRole;
}
