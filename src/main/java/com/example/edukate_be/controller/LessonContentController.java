package com.example.edukate_be.controller;

import com.example.edukate_be.dto.AddLessonContentRequest;
import com.example.edukate_be.dto.AddLessonRequest;
import com.example.edukate_be.entity.Lesson;
import com.example.edukate_be.entity.LessonContent;
import com.example.edukate_be.service.LessonContentService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/lesson-content")
public class LessonContentController {
    @Autowired
    private LessonContentService lessonContentService;

    @GetMapping("/{lessonId}")
    public ResponseEntity<List<LessonContent>> getLessonContentsByLessonId(@PathVariable("lessonId") long lessonId) {
        try {
            List<LessonContent> list = lessonContentService.getLessonContentsByLessonId(lessonId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addLessonContent(@RequestBody AddLessonContentRequest addLessonContentRequest){
        try{
            lessonContentService.addLessonContent(addLessonContentRequest);
            return ResponseEntity.ok("Thêm thành công");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/remove/{lessonContentId}")
    public ResponseEntity<String> deleteLessonContent(@PathVariable Long lessonContentId) {
        try {
            lessonContentService.deleteLessonContent(lessonContentId);
            return ResponseEntity.ok("Xóa thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
