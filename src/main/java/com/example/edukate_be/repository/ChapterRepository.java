package com.example.edukate_be.repository;

import com.example.edukate_be.entity.Chapter;
import com.example.edukate_be.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findAll();
    Optional<Chapter> findById(long id);
    void deleteById(long id);
}
