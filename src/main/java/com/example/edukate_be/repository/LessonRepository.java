package com.example.edukate_be.repository;

import com.example.edukate_be.entity.Chapter;
import com.example.edukate_be.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAll();
    Optional<Lesson> findById(long id);

    void deleteById(long id);

    @Modifying
    @Query("DELETE FROM Lesson l WHERE l.chapter.id = :chapterId ")
    void deleteLessonByChapterId(Long chapterId);
}
