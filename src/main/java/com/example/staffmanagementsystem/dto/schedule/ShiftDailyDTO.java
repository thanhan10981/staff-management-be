package com.example.staffmanagementsystem.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDailyDTO {
    private Integer maNhanVien;
    private String tenNhanVien;
    private Integer maCa;
    private String tenCa;
    private String gioBatDau;
    private String gioKetThuc;
}
