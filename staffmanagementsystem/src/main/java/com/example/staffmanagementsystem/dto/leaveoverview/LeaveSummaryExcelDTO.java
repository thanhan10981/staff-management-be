package com.example.staffmanagementsystem.dto.leaveoverview;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LeaveSummaryExcelDTO {
    private String avatar;
    private String tenNhanVien;
    private String email;
    private String tenPhongBan;
    private Integer tongNghiPhepNam;
    private Integer tongNghiBenh;
    private Integer nghiKhongLuongVuot;
    private Integer tongNgayNghi;
    private Integer soNgayConLai;
}
