package com.example.edukate_be.controller;

import com.example.edukate_be.dto.AddCourseRequest;
import com.example.edukate_be.dto.AddInstructorRequest;
import com.example.edukate_be.entity.Course;
import com.example.edukate_be.entity.Instructor;
import com.example.edukate_be.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/instructor")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    @GetMapping("")
    public ResponseEntity<List<Instructor>> getAllInstructor() {
        try {
            List<Instructor> list = instructorService.getAllInstructor();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addInstructor(@ModelAttribute AddInstructorRequest addInstructorRequest) {
        try{
            instructorService.addInstructor(addInstructorRequest);
            return ResponseEntity.ok("Thêm thành công");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
