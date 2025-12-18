package com.example.staffmanagementsystem.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichTrucNgayDTO {

    private Integer maLichTruc;
    private Integer maNhanVien;
    private String hoTen;

    private Integer maCa;
    private Integer maPhong;

    private LocalDate ngayTruc;
    private String trangThai;
    private String ghiChu;
}
