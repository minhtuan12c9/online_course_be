package com.example.edukate_be.controller;

import com.example.edukate_be.dto.AddCourseReviewRequest;
import com.example.edukate_be.dto.AddUserCourseRequest;
import com.example.edukate_be.dto.InfoMyCourseResponse;
import com.example.edukate_be.entity.Course;
import com.example.edukate_be.entity.CourseReview;
import com.example.edukate_be.entity.UserCourse;
import com.example.edukate_be.service.CourseService;
import com.example.edukate_be.service.UserCourseService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/user-course")
public class UserCourseController {
    @Autowired
    private UserCourseService userCourseService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public ResponseEntity<String> addUserCourse(@RequestBody AddUserCourseRequest addUserCourseRequest) {
        try{
            userCourseService.addUserCourse(addUserCourseRequest);
            return ResponseEntity.ok("Thêm thành công");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{courseId}/{userId}")
    public ResponseEntity<Optional<UserCourse>> getUserCourseByCourseIdAndUserId(@PathVariable("courseId") long courseId, @PathVariable("userId") long userId) {
        try {
            Optional<UserCourse> userCourse = userCourseService.getUserCourseByCourseIdAndUserId(courseId, userId);
            return ResponseEntity.ok(userCourse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<InfoMyCourseResponse>> getAllCourses(@RequestParam("userId") long userId) {
        try {
            List<InfoMyCourseResponse> list = userCourseService.getAllCourses(userId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
