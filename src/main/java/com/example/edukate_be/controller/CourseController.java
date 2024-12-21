package com.example.edukate_be.controller;

import com.example.edukate_be.dto.AddCourseRequest;
import com.example.edukate_be.entity.Course;
import com.example.edukate_be.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("")
    public ResponseEntity<List<Course>> getAllCourses() {
        try {
            List<Course> list = courseService.getAllCourses();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Optional<Course>> getDetailCourseByCourseId(@PathVariable Long courseId) {
        try {
            Optional<Course> course = courseService.getCourseById(courseId);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@ModelAttribute AddCourseRequest addCourseRequest){
        try{
            courseService.addCourse(addCourseRequest);
            return ResponseEntity.ok("Thêm thành công");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCoursesByName(@RequestParam("name") String name) {
        try {
            List<Course> courses = courseService.searchCoursesByName(name);
            if (!courses.isEmpty()) {
                return ResponseEntity.ok(courses);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Không tìm thấy khóa học nào với tên: " + name);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi trong khi tìm kiếm khóa học: " + e.getMessage());
        }
    }

}
