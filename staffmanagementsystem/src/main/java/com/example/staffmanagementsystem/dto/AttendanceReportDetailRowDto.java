package com.example.staffmanagementsystem.dto;

import lombok.Data;

@Data
public class AttendanceReportDetailRowDto {
    private Long employeeId; // MaNhanVien
    private String employeeName; // HoTen
    private String departmentName; // TenPhongBan
    private long workingDays;
    private long lateCount;
    private long unpaidLeaveDays; // từ DonNghiPhep với LoaiNghi = 'Nghi khong luong'
    private double onTimeRate;
    private String riskColor; // GREEN / YELLOW / RED
}