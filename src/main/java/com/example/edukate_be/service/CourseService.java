package com.example.edukate_be.service;

import com.example.edukate_be.dto.AddCourseRequest;
import com.example.edukate_be.dto.UpdateCourseRequest;
import com.example.edukate_be.dto.UpdateUserRequest;
import com.example.edukate_be.entity.Course;
import com.example.edukate_be.entity.Instructor;
import com.example.edukate_be.entity.User;
import com.example.edukate_be.repository.CourseRepository;
import com.example.edukate_be.repository.InstructorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    public List<Course> searchCoursesByName(String name) {
        return courseRepository.findByNameCourse(name.trim());
    }

    @Transactional
    public Course updateCourse(Long courseId, UpdateCourseRequest updateCourseRequest) {
        try {
            // Kiểm tra story có tồn tại không
            Optional<Course> existingCourseOptional = courseRepository.findById(courseId);
            if (existingCourseOptional.isPresent()) {
                Course existingCourse = existingCourseOptional.get();
                System.out.println(updateCourseRequest);

                if(updateCourseRequest.getName() != null && !updateCourseRequest.getName().isEmpty()){
                    existingCourse.setName(updateCourseRequest.getName());
                }

                if(updateCourseRequest.getDescription() != null && !updateCourseRequest.getDescription().isEmpty()){
                    existingCourse.setDescription(updateCourseRequest.getDescription());
                }

                if(updateCourseRequest.getImage() != null){
                    String avtImageUrl = saveImage(updateCourseRequest.getImage());
                    existingCourse.setCoverImage(avtImageUrl);
                }

                if (updateCourseRequest.getInstructorId() > 0) {
                    Optional<Instructor> optionalInstructor = instructorRepository.findById(updateCourseRequest.getInstructorId());
                    if (optionalInstructor.isPresent()) {
                        Instructor instructor = optionalInstructor.get();
                        existingCourse.setInstructor(instructor);
                    } else {
                        throw new EntityNotFoundException("Instructor with ID " + updateCourseRequest.getInstructorId() + " not found.");
                    }
                }

                if(updateCourseRequest.getPrice() != null){
                    existingCourse.setPrice(updateCourseRequest.getPrice());
                }

                return courseRepository.save(existingCourse);

            } else {
                throw new RuntimeException("Không tìm thấy tài khoản có ID: " + courseId);
            }
        } catch (Exception e) {
            System.out.println("error::" + e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }
}
