package com.example.staffmanagementsystem.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TiemChungDTO {

    private Integer maTiemChung;
    private Integer maNhanVien;    // để FE gửi lên
    private String tenNhanVien;    // hiển thị FE (JOIN)
    private String loai;
    private LocalDate ngayTiem;
    private String ketQua;
    private String ghiChu;
}
