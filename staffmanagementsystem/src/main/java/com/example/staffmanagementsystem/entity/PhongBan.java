package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PhongBan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhongBan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPhongBan")
    private Integer id;

    @Column(name = "TenPhongBan")
    private String tenPhongBan;

    @Column(name = "MoTa")
    private String moTa;

    @ManyToOne
    @JoinColumn(name = "MaKhoa") // khóa ngoại -> bảng Khoa
    private Khoa khoa;
}
