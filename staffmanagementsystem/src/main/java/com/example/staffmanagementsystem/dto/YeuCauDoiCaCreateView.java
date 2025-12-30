package com.example.staffmanagementsystem.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class YeuCauDoiCaCreateView {

    // nhân viên hiện tại
    private Integer maNhanVien;
    private String tenNhanVien;

    // ca hiện tại
    private Integer maCaHienTai;
    private String tenCaHienTai;
    private LocalDate ngayTruc;

    // danh sách nhân viên để chọn
    private List<EmployeeOption> nhanVienOptions;

    // danh sách ca để chọn
    private List<ShiftOption> caOptions;
}
