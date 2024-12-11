package com.example.edukate_be.service;

import com.example.edukate_be.dto.AddCourseReviewRequest;
import com.example.edukate_be.dto.AddOneUserRequest;
import com.example.edukate_be.entity.Course;
import com.example.edukate_be.entity.CourseReview;
import com.example.edukate_be.entity.User;
import com.example.edukate_be.repository.CourseRepository;
import com.example.edukate_be.repository.CourseReviewRepository;
import com.example.edukate_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CourseReviewService {

    @Autowired
    private CourseReviewRepository courseReviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<CourseReview> getAllCourseReviews() {
        return courseReviewRepository.findAll();
    }

    public void addCourseReview(AddCourseReviewRequest addCourseReviewRequest){
        CourseReview courseReview = new CourseReview();
        courseReview.setRating(addCourseReviewRequest.getRating());
        courseReview.setComment(addCourseReviewRequest.getComment());

        // Nếu `createdAt` không được cung cấp, tự động set thời gian hiện tại
        if (addCourseReviewRequest.getCreatedAt() == null) {
            courseReview.setCreatedAt(LocalDateTime.now());
        } else {
            courseReview.setCreatedAt(addCourseReviewRequest.getCreatedAt());
        }

        // Set User Id
        Long userId = addCourseReviewRequest.getUserId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }
        courseReview.setUser(optionalUser.get());

        // Set Course Id
        Long courseId = addCourseReviewRequest.getCourseId();
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            throw new IllegalArgumentException("Course with ID " + userId + " does not exist.");
        }
        courseReview.setCourse(optionalCourse.get());

        courseReviewRepository.save(courseReview);
    }
}
