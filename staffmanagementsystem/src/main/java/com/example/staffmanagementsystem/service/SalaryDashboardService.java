package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.DashboardSalaryDto;
import com.example.staffmanagementsystem.dto.SalaryChartDto;
import com.example.staffmanagementsystem.dto.SalaryDetailDto;

import java.util.List;

public interface SalaryDashboardService {
    DashboardSalaryDto getOverview(int month, int year);
    List<SalaryDetailDto> getSalaryDetail(int month, int year);
    SalaryChartDto getChartData(int month, int year);
    void calculateSalary(int month, int year);
    void updateHolidayCoef(double holidayCoef, double weekendCoef);
}
