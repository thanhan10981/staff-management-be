package com.example.staffmanagementsystem.dto.schedule;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayDetailScheduleDTO {

    private Integer maLichTruc;

    private String anhDaiDien;
    private String hoTen;
    private String tenViTri;
    private String tenPhong;
    private String tenKhoa;
    private String tenCa;

    private BigDecimal tongGioLam;
    private String trangThai;
}
