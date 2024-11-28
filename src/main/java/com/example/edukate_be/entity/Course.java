package com.example.edukate_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Column(name = "instructor_id")
    private Long instructorId;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid = true;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "total_duration_minutes", nullable = false)
    private Integer totalDurationMinutes = 0;


}
