package com.example.edukate_be.controller;

import com.example.edukate_be.dto.AddCourseReviewRequest;
import com.example.edukate_be.entity.Course;
import com.example.edukate_be.entity.CourseReview;
import com.example.edukate_be.service.CourseReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/course-review")
public class CourseReviewController {

    @Autowired
    private CourseReviewService courseReviewService;

    @GetMapping("")
    public ResponseEntity<List<CourseReview>> getAllCourseReviews() {
        try {
            List<CourseReview> list = courseReviewService.getAllCourseReviews();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCourseReview(@RequestBody AddCourseReviewRequest addCourseReviewRequest){
        try{
            courseReviewService.addCourseReview(addCourseReviewRequest);
            return ResponseEntity.ok("Thêm thành công");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
