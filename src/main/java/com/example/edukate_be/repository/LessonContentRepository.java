package com.example.edukate_be.repository;

import com.example.edukate_be.entity.Lesson;
import com.example.edukate_be.entity.LessonContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonContentRepository extends JpaRepository<LessonContent, Long> {
    List<LessonContent> findAll();
    Optional<LessonContent> findById(long id);
    void deleteById(long id);
}
