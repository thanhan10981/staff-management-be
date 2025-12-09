package com.example.staffmanagementsystem.dto.schedule;

import lombok.*;
import com.example.staffmanagementsystem.entity.NhanVien;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVienScheduleDTO {

    private Integer maNhanVien;
    private String tenNhanVien;

    private Integer maPhongBan;
    private String tenPhongBan;

    private Integer maKhoa;
    private String tenKhoa;

    public static NhanVienScheduleDTO fromEntity(NhanVien nv) {
        if (nv == null) return null;

        return NhanVienScheduleDTO.builder()
                .maNhanVien(nv.getMaNhanVien())
                .tenNhanVien(nv.getTenNhanVien())
                .maPhongBan(nv.getPhongBan() != null ? nv.getPhongBan().getId() : null)
                .tenPhongBan(nv.getPhongBan() != null ? nv.getPhongBan().getTenPhongBan() : null)
                .maKhoa(nv.getKhoa() != null ? nv.getKhoa().getId() : null)
                .tenKhoa(nv.getKhoa() != null ? nv.getKhoa().getTenKhoa() : null)
                .build();
    }
}
