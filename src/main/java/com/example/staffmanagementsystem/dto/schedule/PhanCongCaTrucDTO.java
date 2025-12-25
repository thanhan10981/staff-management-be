package com.example.staffmanagementsystem.dto.schedule;


import lombok.Data;
import java.time.LocalDate;


@Data
public class PhanCongCaTrucDTO {
    private Integer maPhanCong;
    private Integer maNhanVien; // optional: can be provided by email lookup
    private String email; // optional
    private Integer maCa;
    private Integer maPhong;
    private Integer maKhoa;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private Integer lapLaiHangTuan; // number of weeks to repeat
    private String ghiChu;
}
