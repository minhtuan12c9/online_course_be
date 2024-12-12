package com.example.edukate_be.service;

import com.example.edukate_be.dto.AddCourseReviewRequest;
import com.example.edukate_be.dto.AddUserCourseRequest;
import com.example.edukate_be.entity.Course;
import com.example.edukate_be.entity.CourseReview;
import com.example.edukate_be.entity.User;
import com.example.edukate_be.entity.UserCourse;
import com.example.edukate_be.repository.CourseRepository;
import com.example.edukate_be.repository.UserCourseRepository;
import com.example.edukate_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserCourseService {

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public void addUserCourse(AddUserCourseRequest addUserCourseRequest){
        UserCourse userCourse = new UserCourse();
        userCourse.setEnrollmentDate(LocalDateTime.now());
        userCourse.setPaymentStatus(addUserCourseRequest.getPaymentStatus());
        userCourse.setTotalTimeSpent(0);

        // Set User Id
        Long userId = addUserCourseRequest.getUserId();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist.");
        }
        userCourse.setUser(optionalUser.get());

        // Set Course Id
        Long courseId = addUserCourseRequest.getCourseId();
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            throw new IllegalArgumentException("Course with ID " + userId + " does not exist.");
        }
        userCourse.setCourse(optionalCourse.get());

        userCourseRepository.save(userCourse);
    }

    public Optional<UserCourse> getUserCourseByCourseIdAndUserId(long courseId, long userId) {
        return userCourseRepository.findByCourseIdAndUserId(courseId, userId);
    }
}
