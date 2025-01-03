package com.example.edukate_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_progress")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_progress_ibfk_1"))
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", foreignKey = @ForeignKey(name = "user_progress_ibfk_2"))
    @JsonBackReference
    private Lesson lesson;

    @Column(name = "last_viewed", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastViewed;

    @Column(name = "is_completed", columnDefinition = "INT DEFAULT 0")
    private Integer isCompleted;

    @Column(name = "is_unlock", columnDefinition = "INT DEFAULT 0")
    private Integer isUnlock;

    @Column(name = "time_spent_minutes", columnDefinition = "INT DEFAULT 0")
    private Integer timeSpentMinutes;
}
