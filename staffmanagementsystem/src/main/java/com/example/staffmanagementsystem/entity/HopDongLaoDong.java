package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "HopDongLaoDong")
public class HopDongLaoDong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHopDong")
    private Integer maHopDong;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVien nhanVien;

    @Column(name = "SoHopDong")
    private String soHopDong;

    @Column(name = "LoaiHopDong")
    private String loaiHopDong;

    @Column(name = "NgayBatDau")
    private LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc")
    private LocalDate ngayKetThuc;

    @Column(name = "LuongCoBan")
    private Double luongCoBan;

    @Column(name = "TepDinhKem")
    private String tepDinhKem;

    @Column(name = "TrangThai")
    private String trangThai;
}
