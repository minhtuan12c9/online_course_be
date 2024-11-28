package com.example.edukate_be.repository;

import com.example.edukate_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Kiểm tra sự tồn tại của email
    boolean existsByEmail(String email);

    List<User> findAll();
    Optional<User> findById(Long id);
    @Query("SELECT u FROM User u WHERE u.email = :email and u.password = :password")
    Optional<User> findByEmailAndPassword(String email, String password);
}
