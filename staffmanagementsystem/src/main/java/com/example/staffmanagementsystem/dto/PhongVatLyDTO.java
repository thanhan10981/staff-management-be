package com.example.staffmanagementsystem.dto;


import com.example.staffmanagementsystem.entity.PhongVatLy;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhongVatLyDTO {

    private Integer maPhong;
    private String soPhong;
    private String tenPhong;
    private String loaiPhong;

    private Integer maKhoa;
    private String tenKhoa;

    public static PhongVatLyDTO fromEntity(PhongVatLy p) {
        if (p == null) return null;

        return PhongVatLyDTO.builder()
                .maPhong(p.getMaPhong())
                .soPhong(p.getSoPhong())
                .tenPhong(p.getTenPhong())
                .loaiPhong(p.getLoaiPhong())
                .maKhoa(p.getKhoa() != null ? p.getKhoa().getId() : null)
                .tenKhoa(p.getKhoa() != null ? p.getKhoa().getTenKhoa() : null)
                .build();
    }
}
