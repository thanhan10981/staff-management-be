package com.example.staffmanagementsystem.dto.profile;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongTinTongQuanNhanVienDto {

    private String hoTen;
    private String anhDaiDien;
    private String tenViTri;
    private LocalDateTime dangNhapCuoi;
}
