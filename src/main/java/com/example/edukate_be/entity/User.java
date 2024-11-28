package com.example.edukate_be.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", nullable = false, length = 250)
    private String fullname;

    @Column(name = "email", nullable = false, length = 250, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 250)
    private String password;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin = false;

    @Column(name = "avatar", length = 250)
    private String avatar = "avt.jpg";

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


}