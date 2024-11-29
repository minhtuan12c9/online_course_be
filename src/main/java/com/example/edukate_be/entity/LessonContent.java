package com.example.edukate_be.entity;

import jakarta.persistence.*;
import jdk.jfr.ContentType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lesson_content")
@Data
@Getter
@Setter
public class LessonContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
}
