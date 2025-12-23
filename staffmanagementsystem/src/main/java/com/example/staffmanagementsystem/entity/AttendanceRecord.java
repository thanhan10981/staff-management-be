package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "ChamCong")
@Getter
@Setter
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChamCong")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "MaLichTruc")
    private LichTrucNgay lichTrucNgay;

    // TẠM THỜI COMMENT HOẶC XÓA QUAN HỆ NÀY ĐỂ KHẮC PHỤC LỖI
    // Nếu sau này cần QR, bạn tạo entity AttendanceQrToken với @Id Integer
    // @ManyToOne
    // @JoinColumn(name = "MaQR")
    // private AttendanceQrToken qrToken;

    // Thay bằng kiểu đơn giản nếu cần lưu MaQR
    @Column(name = "MaQR")
    private String maQR;  // ← int thay vì relation

    @Column(name = "ThoiGianVao")
    private LocalDateTime checkInTime;

    @Column(name = "ThoiGianRa")
    private LocalDateTime checkOutTime;

    @Column(name = "TrangThai", length = 20)
    private String status;

    @Column(name = "ThietBi", length = 100)
    private String deviceInfo;

    // MaNV là nvarchar → dùng String
    @Column(name = "MaNV", length = 50, nullable = true)
    private String maNV;

    @Transient
    private NhanVien nhanVien;

    @Transient
    private Long totalMinutes;

    public void computeTotalMinutes() {
        if (checkInTime != null && checkOutTime != null && !checkOutTime.isBefore(checkInTime)) {
            this.totalMinutes = Duration.between(checkInTime, checkOutTime).toMinutes();
        } else {
            this.totalMinutes = 0L;
        }
    }
}