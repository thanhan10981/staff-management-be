package com.example.staffmanagementsystem.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceReportFilter {
    private LocalDate fromDate;
    private LocalDate toDate;
    private Long departmentId; // MaPhongBan
    private Long positionId;   // MaViTri
    private Long employeeId;   // MaNhanVien
}