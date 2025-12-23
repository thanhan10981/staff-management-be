package com.example.staffmanagementsystem.dto.attendancesummary;

import java.time.LocalDate;

public interface TiLeDungGioDTO {

    LocalDate getTuNgay();
    LocalDate getDenNgay();
    Long getSoLanDungGio();
    Long getTongSoLanDiLam();
    default Double getTiLeDungGio() {
        if (getTongSoLanDiLam() == null || getTongSoLanDiLam() == 0) return 0.0;
        return 100.0 * getSoLanDungGio() / getTongSoLanDiLam();
    }
}
