package com.example.staffmanagementsystem.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhanCongCaTrucDTO {

    private Integer maPhanCong;
    private Integer maNhanVien;
    private String hoTen;

    private Integer maCa;
    private Integer maPhong;
    private Integer maKhoa;

    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    private Integer lapLaiHangTuan;
    private String trangThai;

    private String ghiChu;
}
