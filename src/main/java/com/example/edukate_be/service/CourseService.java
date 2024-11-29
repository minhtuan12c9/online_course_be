package com.example.edukate_be.service;

import com.example.edukate_be.dto.AddCourseRequest;
import com.example.edukate_be.entity.Course;
import com.example.edukate_be.entity.Instructor;
import com.example.edukate_be.repository.CourseRepository;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }


    public void addCourse(AddCourseRequest addCourseRequest) {
        // Lưu hình ảnh và lấy URL
        String imgUrl = saveImage(addCourseRequest.getImgFile());

        // Tạo mới đối tượng Course
        Course course = new Course();
        course.setName(addCourseRequest.getName());
        course.setDescription(addCourseRequest.getDescription());
        course.setCoverImage(imgUrl);

        //
        Long instructorId = addCourseRequest.getInstructorId();
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);

        if (optionalInstructor.isEmpty()) {
            throw new IllegalArgumentException("Instructor with ID " + instructorId + " does not exist.");
        }

        // Gán giảng viên cho khóa học
        course.setInstructor(optionalInstructor.get());

        // Gán các thông tin khác
        course.setPrice(addCourseRequest.getPrice());
        course.setTotalDurationMinutes(addCourseRequest.getTotalDurationMinutes());

        // Lưu khóa học vào cơ sở dữ liệu
        courseRepository.save(course);
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
