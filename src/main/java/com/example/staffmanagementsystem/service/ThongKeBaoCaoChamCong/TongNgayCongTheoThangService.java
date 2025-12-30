package com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong;


import com.example.staffmanagementsystem.dto.attendancesummary.TongNgayCongTheoThangDTO;

import java.time.LocalDate;
import java.util.List;

public interface TongNgayCongTheoThangService {

    List<TongNgayCongTheoThangDTO> thongKeTheoNam(
            LocalDate ngayChon,
            Integer maPhongBan,
            Integer maViTri
    );
}

