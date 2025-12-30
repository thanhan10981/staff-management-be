package com.example.staffmanagementsystem.dto.profile;


import lombok.*;
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
