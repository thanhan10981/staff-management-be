package com.example.staffmanagementsystem.dto;

import lombok.Data;

@Data
public class AttendanceReportSummaryDto {
    private long totalWorkingDays;
    private long lateCount;
    private long absentCount;
    private double onTimeRate; // %
}