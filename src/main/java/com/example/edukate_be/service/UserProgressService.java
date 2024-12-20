package com.example.edukate_be.service;

import com.example.edukate_be.dto.UpdateUserProgressRequest;
import com.example.edukate_be.dto.UpdateUserRequest;
import com.example.edukate_be.entity.Lesson;
import com.example.edukate_be.entity.User;
import com.example.edukate_be.entity.UserProgress;
import com.example.edukate_be.repository.LessonRepository;
import com.example.edukate_be.repository.UserProgressRepository;
import com.example.edukate_be.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProgressService {
    @Autowired
    private UserProgressRepository userProgressRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String updateUserProgress(Long userId, Long lessonId, UpdateUserProgressRequest updateUserProgressRequest) {
        try {
            // Kiểm tra story có tồn tại không
            Optional<UserProgress> existingUserProgressOptional = userProgressRepository.findByUserIdAndLessonId(userId, lessonId);
            UserProgress existingUserProgress;

            if (existingUserProgressOptional.isPresent()) {
                // If the record exists, retrieve it
                existingUserProgress = existingUserProgressOptional.get();
            } else {
                // If the record doesn't exist, create a new one
                existingUserProgress = new UserProgress();
                userRepository.findById(userId).ifPresent(existingUserProgress::setUser); // Ensure user exists
                lessonRepository.findById(lessonId).ifPresent(existingUserProgress::setLesson); // Ensure lesson exists
                existingUserProgress.setIsCompleted(0);
                existingUserProgress.setTimeSpentMinutes(0);
                existingUserProgress = userProgressRepository.save(existingUserProgress);
            }


            if(existingUserProgress.getIsCompleted() == 1){
                return "Completed";
            }

            if(updateUserProgressRequest.getTimeSpentMinutes() == null)
                throw new RuntimeException("Không tìm thấy tài khoản có ID: " + userId);

            int newMinutes = existingUserProgress.getTimeSpentMinutes() + updateUserProgressRequest.getTimeSpentMinutes();
            Optional<Lesson> lessionOptional = lessonRepository.findById(lessonId);
            if (lessionOptional.isEmpty())
                throw new RuntimeException("Không tìm thấy tài khoản có ID: " + userId);

            Lesson lesson = lessionOptional.get();

            if (newMinutes >= lesson.getDurationMinutes()) {
                existingUserProgress.setTimeSpentMinutes(lesson.getDurationMinutes());
                existingUserProgress.setIsCompleted(1);
            }else
                existingUserProgress.setTimeSpentMinutes(newMinutes);

            return "Updated";
        } catch (Exception e) {
            System.out.println("error::" + e);
            throw new RuntimeException(e);
        }
    }
}
