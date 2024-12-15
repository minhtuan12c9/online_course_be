package com.example.edukate_be.service;

import com.example.edukate_be.dto.AddOneUserRequest;
import com.example.edukate_be.dto.LoginUserRequest;
import com.example.edukate_be.dto.UpdateUserRequest;
import com.example.edukate_be.entity.User;
import com.example.edukate_be.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
//@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }
    public void findById(Long id) {
        userRepository.findById(id);
    }

    public User addOneUser( AddOneUserRequest addOneUserRequest){
        User user = new User();
        user.setFullname(addOneUserRequest.getFullname());
        user.setEmail(addOneUserRequest.getEmail());
        user.setPassword(addOneUserRequest.getPassword());
        return userRepository.save(user);
    }

    public Optional<User> loginUser(LoginUserRequest loginUserRequest){
        return userRepository.findByEmailAndPassword(loginUserRequest.getEmail(), loginUserRequest.getPassword());
    }

    @Transactional
    public User updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        try {
            // Kiểm tra story có tồn tại không
            Optional<User> existingUserOptional = userRepository.findById(userId);
            if (existingUserOptional.isPresent()) {
                User existingUser = existingUserOptional.get();
                System.out.println(updateUserRequest);

                if(updateUserRequest.getFullname() != null && !updateUserRequest.getFullname().isEmpty()){
                    existingUser.setFullname(updateUserRequest.getFullname());
                }

                if(updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()){
                    existingUser.setPassword(updateUserRequest.getPassword());
                }

                if(updateUserRequest.getAvatar() != null){
                    String avtImageUrl = saveImage(updateUserRequest.getAvatar());
                    existingUser.setAvatar(avtImageUrl);
                }

                return userRepository.save(existingUser);

            } else {
                throw new RuntimeException("Không tìm thấy tài khoản có ID: " + userId);
            }
        } catch (Exception e) {
            System.out.println("error::" + e);
            throw new RuntimeException(e);
        }
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
