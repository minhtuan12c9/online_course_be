package com.example.edukate_be.service;

import com.example.edukate_be.dto.AddChapterRequest;
import com.example.edukate_be.dto.AddCourseRequest;
import com.example.edukate_be.entity.Chapter;
import com.example.edukate_be.entity.Course;
import com.example.edukate_be.entity.Instructor;
import com.example.edukate_be.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChapterService {
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LessonContentRepository lessonContentRepository;

    public Optional<Chapter> getChapterById(Long id) {
        return chapterRepository.findById(id);
    }

    public List<Chapter> getAllChapters() {
        return chapterRepository.findAll();
    }

    public List<Chapter> getChaptersByCourseId(long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            return course.getChapters(); // Giả sử `Course` có liên kết tới `Chapter` với kiểu `OneToMany`
        }
        return List.of();
    }

    public void addChapter(AddChapterRequest addChapterRequest) {

        // Tạo mới đối tượng Chapter
        Chapter chapter = new Chapter();
        chapter.setName(addChapterRequest.getName());
        chapter.setDescription(addChapterRequest.getDescription());

        //
        Long courseId = addChapterRequest.getCourseId();
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isEmpty()) {
            throw new IllegalArgumentException("Instructor with ID " + courseId + " does not exist.");
        }

        chapter.setCourse(optionalCourse.get());

        // Lưu khóa học vào cơ sở dữ liệu
        chapterRepository.save(chapter);
    }

    @Transactional
    public void deleteChapter(Long chapterId) {
        chapterRepository.deleteById(chapterId);
    }
}
