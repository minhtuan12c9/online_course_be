package com.example.edukate_be.dto;

import lombok.Data;

@Data
public class InfoMyCourseResponse {
    Long id;
    String cover_imageCourse;
    String nameCourse;
//    String timeCourse;
    int sum;
    float  ratingCourseReview;
    float timePercentCourse;
}
