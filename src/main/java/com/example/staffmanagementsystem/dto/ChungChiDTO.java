package com.example.staffmanagementsystem.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChungChiDTO {

    private Integer id;

    private Integer maNhanVien;     // FK
    private String tenNhanVien;     // Lấy để hiển thị

    private String soChungChi;
    private LocalDate ngayCap;
    private String noiCap;
    private LocalDate ngayHetHan;
    private String trangThai;
    private String tepDinhKem;
}
