package com.example.staffmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CTLuongDTO {

    private Long luongCoBan;
    private Long phuCapTrucCa;
    private Long phuCapPhauThuat;
    private Long phuCapDocHai;
    private Long phuCapAnCa;
    private Long phuCapKhac;
    private Long phuCapLeTet;

    private Long tongThuNhap;
    private Long thueTncn;
    private Long bhxh;
    private Long thucLanh;
}
