package com.example.staffmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BangLuongNhanVienDTO {
    private String tenNhanVien;
    private String phongBan;
    private Double luongCoBan;
    private Double phuCap;
    private Double thuong;
    private Double tong;
}
