package com.example.edukate_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_course")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Chỉ cho phép serialize từ User -> UserCourse
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonBackReference // Chỉ cho phép serialize từ Course -> UserCourse
    private Course course;

    @Column(name = "enrollment_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime enrollmentDate;

    @Column(name = "payment_status", nullable = false)
    private int paymentStatus;

    @Column(name = "total_time_spent", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int totalTimeSpent = 0;
}
