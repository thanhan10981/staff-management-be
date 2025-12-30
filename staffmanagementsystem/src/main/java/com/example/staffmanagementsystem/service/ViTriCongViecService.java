package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.ViTriCongViecDTO;
import com.example.staffmanagementsystem.entity.ViTriCongViec;

import java.util.List;

public interface ViTriCongViecService {
    List<ViTriCongViec> findAll();
    List<ViTriCongViecDTO> getViTriTheoPhongBan(Integer maPhongBan);
}

