package com.example.staffmanagementsystem.dto;


import lombok.Data;
import java.util.List;

    @Data
    public class SalaryChartDto {
        private List<String> days;   // "T2","T3","T4"...

        private List<Long> onTime;   // Tổng giờ làm mỗi ngày

        private List<Long> late;
    }
