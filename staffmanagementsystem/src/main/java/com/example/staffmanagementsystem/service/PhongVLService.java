package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.PhongVatLyDTO;

import java.util.List;

public interface PhongVLService {
    List<PhongVatLyDTO> getPhongTheoKhoa(int maKhoa);
}
