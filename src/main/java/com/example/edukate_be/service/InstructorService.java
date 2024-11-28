package com.example.edukate_be.service;

import com.example.edukate_be.dto.AddInstructorRequest;
import com.example.edukate_be.entity.Course;
import com.example.edukate_be.entity.Instructor;
import com.example.edukate_be.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class InstructorService {
    @Autowired
    private InstructorRepository instructorRepository;

    public List<Instructor> getAllInstructor() {
        return instructorRepository.findAll();
    }
    public void addInstructor(AddInstructorRequest addInstructorRequest) {
        String avtUrl = saveImage(addInstructorRequest.getAvatar());
        Instructor instructor = new Instructor();
        instructor.setName(addInstructorRequest.getName());
        instructor.setPhone(addInstructorRequest.getPhone());
        instructor.setPosition(addInstructorRequest.getPosition());
        instructor.setAvatar(avtUrl);
        instructor.setLink(addInstructorRequest.getLink());
        instructorRepository.save(instructor);
    }

    public String saveImage(MultipartFile image) {
        try {
            String uploadDir = "uploads/";

            String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Lưu ảnh vào thư mục
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Trả về URL của ảnh đã lưu
            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu trữ tệp ảnh: " + e.getMessage());
        }
    }
}
