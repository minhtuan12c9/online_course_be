package com.example.edukate_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "chapter")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnoreProperties("chapters")
    private Course course;

    @Column(nullable = false, length = 250)
    private String name;

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "chapter")
    @JsonIgnoreProperties("chapter")
    private List<Lesson> lessons;

}
