package com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong;


import com.example.staffmanagementsystem.dto.attendancesummary.TongNghiKhongPhepDTO;

import java.time.LocalDate;

public interface TongNghiKhongPhepService {

    TongNghiKhongPhepDTO tinhTongNghiKhongPhep(
            LocalDate ngayChon,
            String loai,
            Integer maPhongBan,
            Integer maViTri
    );
}
