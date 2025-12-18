package com.example.staffmanagementsystem.service.schedule;
import com.example.staffmanagementsystem.dto.schedule.CaLamViecDTO;
import com.example.staffmanagementsystem.entity.CauHinhCaTruc_Phong;


import java.util.List;


public interface ConfigService {
    List<CaLamViecDTO> getAllCa();
    CauHinhCaTruc_Phong getCauHinh(Integer maPhong, Integer maCa);
}