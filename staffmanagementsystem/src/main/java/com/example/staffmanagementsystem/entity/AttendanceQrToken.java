package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "QR_ChamCong")
@Getter
@Setter
public class AttendanceQrToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaQR")
    private Integer maQR;   // ❗ Long → Integer

    // ===== FK giống QRChamCong =====
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNhanVien", nullable = false)
    private NhanVien nhanVien;   // ❗ Long → Entity

    @Column(name = "NgayTao", nullable = false)
    private LocalDate ngayTao;

    @Column(name = "MaQRCode", nullable = false, length = 255)
    private String maQRCode;

    @Column(name = "TrangThai", length = 20)
    private String trangThai;

    @Transient
    private LocalDateTime expiredAt;
}
