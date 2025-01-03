package com.example.edukate_be.controller;

import com.example.edukate_be.dto.UpdateUserProgressRequest;
import com.example.edukate_be.dto.UpdateUserRequest;
import com.example.edukate_be.dto.UserProgressResponse;
import com.example.edukate_be.entity.User;
import com.example.edukate_be.entity.UserCourse;
import com.example.edukate_be.entity.UserProgress;
import com.example.edukate_be.service.UserProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/user-progress")
public class UserProgressController {
    @Autowired
    private UserProgressService userProgressService;

    @PutMapping("/update/{userId}/{lessonId}")
    public ResponseEntity<String> updateUserProgress(@PathVariable Long userId, @PathVariable Long lessonId, @RequestBody UpdateUserProgressRequest updateUserProgressRequest) {
        try{
            String message = userProgressService.updateUserProgress(userId, lessonId, updateUserProgressRequest);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{userId}/{courseId}")
    public ResponseEntity<List<UserProgressResponse>> getAllByUserIdAndCourseId(@PathVariable("userId") long userId, @PathVariable("courseId") long courseId) {
        try {
            List<UserProgressResponse> userProgress = userProgressService.getAllByUserIdAndCourseId(userId, courseId);
            return ResponseEntity.ok(userProgress);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
