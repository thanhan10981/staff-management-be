package com.example.staffmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class QuyLuongPhongBanDto {
    private String tenPhongBan;
    private double soTien;

}
