package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NguoiDung")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaNguoiDung")
    private Integer maNguoiDung;

    @Column(name = "MaNhanVien")
    private Integer maNhanVien;

    @Column(name = "TenDangNhap", unique = true)
    private String tenDangNhap;

    @Column(name = "MatKhauHash")
    private String matKhauHash;

    @Column(name = "VaiTro")
    private String vaiTro;

    @Column(name = "TrangThai")
    private String trangThai;
}
