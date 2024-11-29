package com.example.edukate_be.controller;

import com.example.edukate_be.dto.AddChapterRequest;
import com.example.edukate_be.dto.AddCourseRequest;
import com.example.edukate_be.entity.Chapter;
import com.example.edukate_be.entity.Course;
import com.example.edukate_be.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @GetMapping("")
    public ResponseEntity<List<Chapter>> getAllChapters() {
        try {
            List<Chapter> list = chapterService.getAllChapters();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addChapter(@RequestBody AddChapterRequest addChapterRequest){
        try{
            chapterService.addChapter(addChapterRequest);
            return ResponseEntity.ok("Thêm thành công");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
