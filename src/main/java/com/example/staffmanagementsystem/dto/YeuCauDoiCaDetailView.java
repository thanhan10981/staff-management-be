package com.example.staffmanagementsystem.dto;

import java.time.LocalDate;

public interface YeuCauDoiCaDetailView {

    Integer getMaYeuCau();

    Integer getNguoiGui();
    String getTenNguoiGui();

    Integer getNguoiNhan();
    String getTenNguoiNhan();

    LocalDate getNgayTruc();

    String getTenCaHienTai();
    String getTenCaMuonDoi();

    String getLyDo();
    String getTrangThai();
}
