package com.example.staffmanagementsystem.dto;

public class PhanQuyenDto {
    private Integer maQuyen;
    private String tenQuyen;
    private String moTa;

    public PhanQuyenDto(Integer maQuyen, String tenQuyen, String moTa) {
        this.maQuyen = maQuyen;
        this.tenQuyen = tenQuyen;
        this.moTa = moTa;
    }

    public Integer getMaQuyen() {
        return maQuyen;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }

    public String getMoTa() {
        return moTa;
    }
}
