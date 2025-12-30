package com.example.staffmanagementsystem.dto.leaveoverview;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveOverviewDTO {

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
