package com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong;


import com.example.staffmanagementsystem.dto.attendancesummary.TongLanDiTreDTO;

import java.time.LocalDate;

public interface TongLanDiTreService {

    TongLanDiTreDTO tinhTongLanDiTre(
            LocalDate ngayChon,
            String loai,
            Integer maPhongBan,
            Integer maViTri
    );
}
