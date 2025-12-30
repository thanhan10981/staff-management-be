package com.example.staffmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApprovalDTO {

    private Integer maNhanVien;
    private String tenNhanVien;
    private String loaiYeuCau;
    private String thoiGian;
    private String chiTiet;
    private String lyDo;
    private Integer id;
}
