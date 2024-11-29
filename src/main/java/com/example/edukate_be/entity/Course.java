package com.example.edukate_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Data
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "cover_image", length = 250)
    private String coverImage;


    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "total_duration_minutes", nullable = false)
    private Integer totalDurationMinutes = 0;

    @ManyToOne
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private Instructor instructor; // Liên kết với giảng viên

    @OneToMany(mappedBy = "course")
    @JsonIgnoreProperties("course")
    private List<Chapter> chapters;
}
