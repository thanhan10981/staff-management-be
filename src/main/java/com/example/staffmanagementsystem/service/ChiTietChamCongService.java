package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.attendancesummary.ChiTietChamCongDTO;

import java.time.LocalDate;
import java.util.List;

public interface ChiTietChamCongService {

    List<ChiTietChamCongDTO> chiTietChamCong(
            LocalDate tuNgay,
            LocalDate denNgay,
            Integer maPhongBan,
            Integer maViTri
    );
}

