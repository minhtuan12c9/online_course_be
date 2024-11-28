package com.example.edukate_be.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class AddCourseRequest {
    private String name;
    private String description;
    private Long instructorId;
    private BigDecimal price;
    private Integer totalDurationMinutes;
    private MultipartFile imgFile;
}
