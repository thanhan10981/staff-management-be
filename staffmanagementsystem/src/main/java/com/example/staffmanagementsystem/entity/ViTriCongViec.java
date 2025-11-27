package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ViTriCongViec")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViTriCongViec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaViTri")
    private Integer id;

    @Column(name = "TenViTri")
    private String tenViTri;

    @Column(name = "MoTa")
    private String moTa;

    @ManyToOne
    @JoinColumn(name = "MaPhongBan")  // khóa ngoại -> bảng PhongBan
    private PhongBan phongBan;
}
