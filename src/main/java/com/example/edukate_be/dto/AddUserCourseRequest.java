package com.example.edukate_be.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddUserCourseRequest {
    private long userId;
    private long courseId;
    private int paymentStatus;
}
