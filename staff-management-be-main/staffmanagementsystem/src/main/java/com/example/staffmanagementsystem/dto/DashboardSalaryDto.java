package com.example.staffmanagementsystem.dto;

import lombok.Data;

@Data
public class DashboardSalaryDto {
    private long totalSalary;
    private long employeePaid;
    private long totalHours;
    private long totalAllowanceOT;
}

