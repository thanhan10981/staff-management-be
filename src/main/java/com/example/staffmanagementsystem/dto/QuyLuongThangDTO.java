package com.example.staffmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuyLuongThangDTO {
    private Number tongHienTai;
    private Number tongThangTruoc;
    private Double tyLe; // %
}
