package com.example.edukate_be.repository;

import com.example.edukate_be.dto.UserProgressResponse;
import com.example.edukate_be.entity.User;
import com.example.edukate_be.entity.UserCourse;
import com.example.edukate_be.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {

    @Query("SELECT up FROM UserProgress up WHERE up.user.id = :userId and up.lesson.id = :lessonId")
    Optional<UserProgress> findByUserIdAndLessonId(Long userId, Long lessonId);

    @Modifying
    @Query("DELETE FROM UserProgress up WHERE up.lesson.id = :lessonId ")
    void deleteUserProgressBylessonId(Long lessonId);

    @Query("SELECT new com.example.edukate_be.dto.UserProgressResponse(up.lesson.id, up.isUnlock) " +
            "FROM UserProgress up WHERE up.user.id = :userId and up.lesson.chapter.course.id = :courseId")
    List<UserProgressResponse> getAllByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);

}
