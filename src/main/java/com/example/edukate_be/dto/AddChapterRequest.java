package com.example.edukate_be.dto;

import lombok.Data;

@Data
public class AddChapterRequest {
    private String name;
    private String description;
    private Long courseId;
}
