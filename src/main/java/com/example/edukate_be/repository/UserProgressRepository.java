package com.example.edukate_be.repository;

import com.example.edukate_be.entity.User;
import com.example.edukate_be.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {

    @Query("SELECT up FROM UserProgress up WHERE up.user.id = :userId and up.lesson.id = :lessonId")
    Optional<UserProgress> findByUserIdAndLessonId(Long userId, Long lessonId);
}
