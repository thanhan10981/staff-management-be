package com.example.staffmanagementsystem.dto;

import java.time.LocalDate;

public interface YeuCauDoiCaView {

    Integer getMaYeuCau();

    Integer getNguoiGui();
    String getTenNguoiGui();

    Integer getNguoiNhan();
    String getTenNguoiNhan();

    Integer getMaCa();
    String getTenCa();

    String getTenPhongBan();

    LocalDate getNgayTruc();
    String getLyDo();
    String getTrangThai();

    // ===== THÊM CHO FORM CREATE =====
    Integer getMaCaHienTai();
    String getTenCaHienTai();
    LocalDate getNgayTrucHienTai();
}
