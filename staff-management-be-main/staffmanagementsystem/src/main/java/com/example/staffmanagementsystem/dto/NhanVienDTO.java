package com.example.staffmanagementsystem.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVienDTO {

    private Integer maNhanVien;
    private String tenNhanVien;
    private LocalDate ngaySinh;
    private Boolean gioiTinh;
    private String sdt;
    private String email;
    private String trangThai;

    private Integer maViTri;
    private Integer maPhongBan;
    private Integer maKhoa;

    private String tenViTri;
    private String tenPhongBan;
    private String tenKhoa;

    private String cccd;
    private LocalDate ngayVaoLam;
    private String trinhDoChuyenMon;

    private String lienHeKhanCap;
    private String sdtLienHeKhanCap;

    private String anhDaiDien;
    private String hopDongFile;
}
