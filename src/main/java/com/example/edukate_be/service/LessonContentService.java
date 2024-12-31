package com.example.edukate_be.service;

import com.example.edukate_be.dto.AddLessonContentRequest;
import com.example.edukate_be.dto.AddLessonRequest;
import com.example.edukate_be.entity.Chapter;
import com.example.edukate_be.entity.Lesson;
import com.example.edukate_be.entity.LessonContent;
import com.example.edukate_be.repository.LessonContentRepository;
import com.example.edukate_be.repository.LessonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonContentService {
    @Autowired
    private LessonContentRepository lessonContentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public List<LessonContent> getLessonContentsByLessonId(long lessonId) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (lessonOptional.isPresent()) {
            Lesson lesson = lessonOptional.get();
            return lesson.getLessonContents(); // Giả sử `Course` có liên kết tới `Chapter` với kiểu `OneToMany`
        }
        return List.of();
    }

    public void addLessonContent(AddLessonContentRequest addLessonContentRequest) {

        // Tạo mới đối tượng Chapter
        LessonContent lessonContent = new LessonContent();
        lessonContent.setContent(addLessonContentRequest.getContent());

        //
        Long lessonId = addLessonContentRequest.getLessonId();
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);

        if (lessonOptional.isEmpty()) {
            throw new IllegalArgumentException("Instructor with ID " + lessonId + " does not exist.");
        }

        lessonContent.setLesson(lessonOptional.get());

        // Lưu khóa học vào cơ sở dữ liệu
        lessonContentRepository.save(lessonContent);
    }

    @Transactional
    public void deleteLessonContent(Long lessonContentId) {
        lessonContentRepository.deleteById(lessonContentId);
    }
}
