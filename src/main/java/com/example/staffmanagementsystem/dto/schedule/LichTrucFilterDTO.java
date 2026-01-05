package com.example.staffmanagementsystem.dto.schedule;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class LichTrucFilterDTO {

    private Integer maKhoa;
    private Integer maPhong;
    private Integer maViTri;
    private Integer maNhanVien;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;
}
