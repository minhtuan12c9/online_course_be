package com.example.edukate_be.dto;

import lombok.Data;

@Data
public class AddLessonRequest {
    private String name;
    private Integer durationMinutes;
    private Long chapterId;
}
