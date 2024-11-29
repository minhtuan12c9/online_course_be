package com.example.edukate_be.dto;

import lombok.Data;

@Data
public class AddLessonContentRequest {
    private String content;
    private Long lessonId;
}
