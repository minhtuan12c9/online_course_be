package com.example.edukate_be.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOneUserRequest {
    private String fullname;
    private String email;
    private String password;

}
