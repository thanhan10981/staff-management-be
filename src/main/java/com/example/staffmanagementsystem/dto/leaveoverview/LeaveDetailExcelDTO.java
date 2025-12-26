package com.example.staffmanagementsystem.dto.leaveoverview;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter @Builder
public class LeaveDetailExcelDTO {
    private Integer maNhanVien;
    private String tenNhanVien;
    private String email;
    private String tenPhongBan;
    private String loaiNghi;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private String lyDo;
    private Integer maDon;
}
