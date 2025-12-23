package com.example.staffmanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class AttendanceCheckInRequest {

    private Long maChamCong;
    private Integer maLichTruc;
    private String maQR;
    private LocalDateTime thoiGianVao;
    private LocalDateTime thoiGianRa;
    private String trangThai;
    private String thietBi;
    private String maNV;

    // getter setter
}

