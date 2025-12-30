package com.example.staffmanagementsystem.dto.attendancesummary;


import java.time.LocalDate;

public interface ChiTietChamCongDTO {

    String getTenNhanVien();
    String getEmail();
    String getTenPhongBan();
    String getTenViTri();

    LocalDate getNgayCong();

    Integer getCoDiLam();
    Integer getDiTre();

    Integer getNghiKhongPhep();
    Integer getNghiCoPhep();
}

