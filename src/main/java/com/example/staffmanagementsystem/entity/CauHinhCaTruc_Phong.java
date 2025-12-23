package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CauHinhCaTruc_Phong")
@IdClass(CauHinhId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CauHinhCaTruc_Phong {

    @Id
    @Column(name = "MaPhong")
    private Integer maPhong;

    @Id
    @Column(name = "MaCa")
    private Integer maCa;

    @Column(name = "SoBacSi")
    private Integer soBacSi;

    @Column(name = "SoYTa")
    private Integer soYTa;

    @Column(name = "SoDieuDuong")
    private Integer soDieuDuong;

    @Column(name = "SoNhanVienKhac")
    private Integer soNhanVienKhac;

    @Column(name = "GhiChu")
    private String ghiChu;
}
