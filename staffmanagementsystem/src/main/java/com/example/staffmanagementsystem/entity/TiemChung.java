package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "TiemChungSucKhoe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TiemChung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTiemChung")
    private Integer maTiemChung;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien")    // FK → bảng NhanVien
    private NhanVien nhanVien;

    @Column(name = "Loai")
    private String loai;

    @Column(name = "NgayTiem")
    private LocalDate ngayTiem;

    @Column(name = "KetQua")
    private String ketQua;

    @Column(name = "GhiChu")
    private String ghiChu;
}
