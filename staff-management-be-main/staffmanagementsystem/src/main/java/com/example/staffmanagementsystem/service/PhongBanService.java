package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.PhongBanDTO;

import java.util.List;

public interface  PhongBanService {
    List<PhongBanDTO> getPhongBanTheoKhoa(int maKhoa);
}
