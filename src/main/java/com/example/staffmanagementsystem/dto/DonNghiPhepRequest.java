package com.example.staffmanagementsystem.dto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DonNghiPhepRequest {

    private Integer maNhanVien;
    private String loaiNghi;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private String lyDo;
}
