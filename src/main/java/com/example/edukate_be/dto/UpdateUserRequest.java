package com.example.edukate_be.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserRequest {
    private String fullname;
    private String password;
    private MultipartFile avatar;
}
