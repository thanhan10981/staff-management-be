package com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong;


import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.dto.attendancesummary.TongNgayCongDTO;

import java.time.LocalDate;

public interface TongNgayCongService {

    TongNgayCongDTO tinhTongNgayCong(
            LocalDate ngayChon,
            LoaiThongKeNgayCong loai,
            Integer maPhongBan,
            Integer maViTri
    );
}

