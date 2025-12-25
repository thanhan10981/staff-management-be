package com.example.staffmanagementsystem.dto;

import lombok.Data;

@Data
public class AttendanceReportFilterWeb {
    private String tuNgay;    // "YYYY-MM-DD" từ frontend
    private String denNgay;   // "YYYY-MM-DD" từ frontend
    private Long maNhanVien;  // optional
    private Long departmentId;
    private Long positionId;
}
