package com.example.staffmanagementsystem.dto.schedule;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDto {
    private Integer maLichTruc;
    private Integer maNhanVien;
    private String tenNhanVien;
    private Integer maCa;
    private String tenCa;
    private Integer maPhong;
    private String tenPhong;
    private String trangThai;
}

