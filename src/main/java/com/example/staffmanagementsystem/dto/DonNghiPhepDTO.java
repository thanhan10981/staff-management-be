package com.example.staffmanagementsystem.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DonNghiPhepDTO {
    private Integer maDon;
    private Integer maNhanVien;
    private String tenNhanVien;   // ✔ thêm tên nhân viên
    private String loaiNghi;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private String lyDo;
    private String trangThai;     // Đã duyệt | Chờ duyệt | Từ chối
}
