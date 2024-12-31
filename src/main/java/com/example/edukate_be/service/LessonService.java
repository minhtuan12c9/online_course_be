package com.example.edukate_be.service;

import com.example.edukate_be.dto.AddChapterRequest;
import com.example.edukate_be.dto.AddLessonRequest;
import com.example.edukate_be.entity.Chapter;
import com.example.edukate_be.entity.Course;
import com.example.edukate_be.entity.Lesson;
import com.example.edukate_be.repository.ChapterRepository;
import com.example.edukate_be.repository.CourseRepository;
import com.example.edukate_be.repository.LessonContentRepository;
import com.example.edukate_be.repository.LessonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonContentRepository lessonContentRepository;

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public List<Lesson> getLessonsByChapterId(long chapterId) {
        Optional<Chapter> chapterOptional = chapterRepository.findById(chapterId);
        if (chapterOptional.isPresent()) {
            Chapter chapter = chapterOptional.get();
            return chapter.getLessons(); // Giả sử `Course` có liên kết tới `Chapter` với kiểu `OneToMany`
        }
        return List.of();
    }

    public void addLesson(AddLessonRequest addLessonRequest) {

        // Tạo mới đối tượng Chapter
        Lesson lesson = new Lesson();
        lesson.setName(addLessonRequest.getName());
        lesson.setDurationMinutes(addLessonRequest.getDurationMinutes());

        //
        Long chapterId = addLessonRequest.getChapterId();
        Optional<Chapter> chapterOptional = chapterRepository.findById(chapterId);

        if (chapterOptional.isEmpty()) {
            throw new IllegalArgumentException("Instructor with ID " + chapterId + " does not exist.");
        }

        lesson.setChapter(chapterOptional.get());
        // Lưu khóa học vào cơ sở dữ liệu
        lessonRepository.save(lesson);

        Course course = chapterOptional.get().getCourse();
        course.setTotalDurationMinutes(course.getTotalDurationMinutes() + addLessonRequest.getDurationMinutes());
        courseRepository.save(course);
    }

    @Transactional
    public void deleteLesson(Long lessonId) {
        lessonContentRepository.deleteById(lessonId);
        lessonRepository.deleteById(lessonId);
    }
}
