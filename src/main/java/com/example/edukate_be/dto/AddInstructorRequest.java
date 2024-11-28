package com.example.edukate_be.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddInstructorRequest {
    private String name;
    private String phone;
    private String position;
    private MultipartFile avatar;
    private String link;
}
