package com.example.edukate_be.service;

import com.example.edukate_be.dto.AddOneUserRequest;
import com.example.edukate_be.dto.LoginUserRequest;
import com.example.edukate_be.entity.User;
import com.example.edukate_be.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
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
}
