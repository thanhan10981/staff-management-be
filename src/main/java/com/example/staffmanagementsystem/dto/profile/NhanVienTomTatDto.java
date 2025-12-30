package com.example.staffmanagementsystem.dto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NhanVienTomTatDto {

    private String tenNhanVien;
    private String email;
    private String anhDaiDien;
    private String tenViTri;

    public NhanVienTomTatDto(
            String tenNhanVien,
            String email,
            String anhDaiDien,
            String tenViTri
    ) {
        this.tenNhanVien = tenNhanVien;
        this.email = email;
        this.anhDaiDien = anhDaiDien;
        this.tenViTri = tenViTri;
    }
}
