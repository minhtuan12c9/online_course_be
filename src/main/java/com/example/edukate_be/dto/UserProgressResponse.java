package com.example.edukate_be.dto;

import lombok.Data;

@Data
public class UserProgressResponse {
    private long lessonId;
    private int isUnlock;

    public UserProgressResponse(long lessonId, int isUnlock) {
        this.lessonId = lessonId;
        this.isUnlock = isUnlock;
    }
}
