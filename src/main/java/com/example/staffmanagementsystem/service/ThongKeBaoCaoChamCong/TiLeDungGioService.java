package com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong;

import com.example.staffmanagementsystem.dto.attendancesummary.TiLeDungGioDTO;

import java.time.LocalDate;

public interface TiLeDungGioService {

    TiLeDungGioDTO tinhTiLeDungGio(
            LocalDate ngayChon,
            String loai,
            Integer maPhongBan,
            Integer maViTri
    );
}
