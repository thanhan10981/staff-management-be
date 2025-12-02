package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PhongVatLy")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhongVatLy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPhong")
    private Integer maPhong;

    @Column(name = "SoPhong")
    private String soPhong;

    @Column(name = "TenPhong")
    private String tenPhong;

    @Column(name = "LoaiPhong")
    private String loaiPhong;
}
