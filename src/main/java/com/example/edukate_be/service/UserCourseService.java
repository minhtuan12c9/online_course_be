package com.example.edukate_be.service;

import com.example.edukate_be.dto.AddCourseReviewRequest;
import com.example.edukate_be.dto.AddUserCourseRequest;
import com.example.edukate_be.dto.InfoMyCourseResponse;
import com.example.edukate_be.entity.*;
import com.example.edukate_be.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    private UserProgressRepository userProgressRepository;

    public List<InfoMyCourseResponse> getAllCourses(long userId) {
        List<InfoMyCourseResponse> listMyCourse = new ArrayList<>();
        List<UserCourse> userCourses = userCourseRepository.findAllByUserId(userId);
        for (UserCourse userCourse : userCourses) {
            InfoMyCourseResponse courseResponse = new InfoMyCourseResponse();
            courseResponse.setId(userCourse.getCourse().getId());
            courseResponse.setNameCourse(userCourse.getCourse().getName());
            courseResponse.setCover_imageCourse(userCourse.getCourse().getCoverImage());

            List<CourseReview> courseReviews = userCourse.getCourse().getCourseReviews();
            int totalRatings = 0;
            for (CourseReview courseReview : courseReviews) {
                totalRatings += courseReview.getRating();
            }
            courseResponse.setSum(courseReviews.size());
            courseResponse.setRatingCourseReview(courseReviews.isEmpty() ? 0 : (float) totalRatings / courseReviews.size());

            List<Chapter> chapters = userCourse.getCourse().getChapters();
            int totalTimeProgress = 0;
            for (Chapter chapter : chapters) {
                List<Lesson> lessons = chapter.getLessons();
                for (Lesson lesson : lessons) {
                    List<UserProgress> userProgress = lesson.getUserProgress();
                    for (UserProgress userProgress1 : userProgress) {
                        if (userProgress1.getUser().getId() == userId) {
                            totalTimeProgress += userProgress1.getTimeSpentMinutes();
                        }
                    }
                }
            }

            courseResponse.setTimePercentCourse(
                    userCourse.getCourse().getTotalDurationMinutes() == 0
                            ? 0
                            : (int) Math.round((float) totalTimeProgress / userCourse.getCourse().getTotalDurationMinutes() * 100)
            );

            listMyCourse.add(courseResponse);
        }
        return listMyCourse;
    }

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

        // Create user progress
        List<Chapter> chapters = userCourse.getCourse().getChapters();
        List<UserProgress> userProgressList = new ArrayList<>();
        int index = 0;

        for (Chapter chapter : chapters) {
            List<Lesson> lessons = chapter.getLessons();
            for (Lesson lesson : lessons) {
                UserProgress userProgress = new UserProgress();
                userProgress.setUser(userCourse.getUser());
                userProgress.setLesson(lesson);
                userProgress.setLastViewed(LocalDateTime.now());
                userProgress.setTimeSpentMinutes(0);
                userProgress.setIsCompleted(0);

                // Mở khóa bài học đầu tiên, các bài khác thì khóa
                userProgress.setIsUnlock(index == 0 ? 1 : 0);

                userProgressList.add(userProgress);
                index++;
            }
        }

// Lưu danh sách UserProgress vào cơ sở dữ liệu
        userProgressRepository.saveAll(userProgressList);

    }

    public Optional<UserCourse> getUserCourseByCourseIdAndUserId(long courseId, long userId) {
        return userCourseRepository.findByCourseIdAndUserId(courseId, userId);
    }
}
