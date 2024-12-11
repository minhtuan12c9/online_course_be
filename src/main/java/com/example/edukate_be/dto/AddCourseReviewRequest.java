package com.example.edukate_be.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddCourseReviewRequest {
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private Long courseId;
    private Long userId;

}
