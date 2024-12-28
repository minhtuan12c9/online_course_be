package com.example.edukate_be.repository;

import com.example.edukate_be.entity.User;
import com.example.edukate_be.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    @Query("SELECT uc FROM UserCourse uc WHERE uc.course.id = :courseId AND uc.user.id = :userId")
    Optional<UserCourse> findByCourseIdAndUserId(@Param("courseId") long courseId, @Param("userId") long userId);

    @Query("SELECT uc FROM UserCourse uc WHERE uc.user.id = :userId")
    List<UserCourse> findAllByUserId(long userId);
}
