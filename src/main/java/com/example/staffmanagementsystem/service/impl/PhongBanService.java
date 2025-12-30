package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.PhongBanDTO;
import com.example.staffmanagementsystem.entity.PhongBan;

import java.util.List;

public interface  PhongBanService {
    List<PhongBanDTO> getPhongBanTheoKhoa(int maKhoa);
    List<PhongBan> findAll();
}
