package com.example.edukate_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "lesson")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "duration_minutes", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer durationMinutes;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    @JsonIgnoreProperties("lessons")
    private Chapter chapter;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnoreProperties("lesson")
    private List<LessonContent> lessonContents;

    @OneToMany(mappedBy = "lesson")
    @JsonIgnoreProperties("lesson")
    private List<UserProgress> userProgress;
}
