package com.example.edukate_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;
import java.util.List;

@Entity
@Table(name = "instructor")  // Tên bảng trong cơ sở dữ liệu
@Getter
@Setter
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động tăng
    @Column(name = "id")
    private Long id;  // Mã giảng viên (khóa chính)

    @Column(name = "name", nullable = false)
    private String name;  // Tên giảng viên

    @Column(name = "phone")
    private String phone;  // Số điện thoại giảng viên

    @Column(name = "position")
    private String position;  // Mô tả về giảng viên

    @Column(name = "avatar")
    private String avatar;  // Đường dẫn đến ảnh đại diện

    @Column(name = "link")
    private String link;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("instructor")
    private List<Course> courses; // Danh sách khóa học liên kết với giảng viên
}
