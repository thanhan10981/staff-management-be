package com.example.staffmanagementsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@Builder
public class LichTrucTuanDTO {

    private Integer maNhanVien;
    private String hoTen;
    private String tenPhong;
    private Integer maKhoa;
    private Integer maPhongBan;
    private Integer maViTri;

    // key = yyyy-MM-dd
    private Map<LocalDate, String> lichTheoNgay;
}