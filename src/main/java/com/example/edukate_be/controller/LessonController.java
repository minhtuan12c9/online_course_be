package com.example.edukate_be.controller;

import com.example.edukate_be.dto.AddChapterRequest;
import com.example.edukate_be.dto.AddLessonRequest;
import com.example.edukate_be.entity.Chapter;
import com.example.edukate_be.entity.Lesson;
import com.example.edukate_be.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @GetMapping("")
    public ResponseEntity<List<Lesson>> getAllLessons() {
        try {
            List<Lesson> list = lessonService.getAllLessons();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{chapterId}")
    public ResponseEntity<List<Lesson>> getLessonsByChapterId(@PathVariable("chapterId") long chapterId) {
        try {
            List<Lesson> list = lessonService.getLessonsByChapterId(chapterId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
        public ResponseEntity<String> addLesson(@RequestBody AddLessonRequest addLessonRequest){
        try{
            lessonService.addLesson(addLessonRequest);
            return ResponseEntity.ok("Thêm thành công");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
