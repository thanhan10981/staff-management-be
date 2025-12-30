package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ChamCong")
public class ChamCong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChamCong")
    private Integer maChamCong;

    // ===== FK → Lịch trực ngày =====
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaLichTruc", nullable = false)
    private LichTrucNgay lichTrucNgay;

    // ===== FK → QR chấm công =====
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaQR", nullable = false)
    private QRChamCong qrChamCong;

    // ===== Mã nhân viên (NVARCHAR, KHÔNG JOIN) =====
    @Column(name = "MaNV")
    private String maNV;

    // ===== Thông tin chấm công =====
    @Column(name = "ThoiGianVao")
    private LocalDateTime thoiGianVao;

    @Column(name = "ThoiGianRa")
    private LocalDateTime thoiGianRa;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name = "ThietBi")
    private String thietBi;
}
