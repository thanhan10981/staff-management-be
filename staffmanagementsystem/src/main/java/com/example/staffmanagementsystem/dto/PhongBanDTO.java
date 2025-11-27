package com.example.staffmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhongBanDTO {
    private Integer maPhongBan;
    private String tenPhongBan;
    private String moTa;
    private Integer maKhoa;
}
