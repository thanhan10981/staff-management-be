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
    private Long id;

    @Column(name = "MaNhanVien", nullable = false)
    private Long employeeId; // map MaNhanVien

    @Column(name = "NgayTao", nullable = false)
    private LocalDate createdAt; // dùng LocalDate cho NgayTao

    @Column(name = "MaQRCode", nullable = false, length = 255)
    private String token; // map MaQRCode làm token

    @Column(name = "TrangThai", length = 20)
    private String status;

    // ExpiredAt tính từ NgayTao + 1 ngày (không có cột, tính runtime)
    @Transient
    private LocalDateTime expiredAt;

    // getters/setters full
}