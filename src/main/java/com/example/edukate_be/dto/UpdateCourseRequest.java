package com.example.edukate_be.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class UpdateCourseRequest {
    private String name;
    private String description;
    private MultipartFile image;
    private long instructorId;
    private BigDecimal price;
}
