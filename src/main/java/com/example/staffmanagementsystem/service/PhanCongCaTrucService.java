package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.PhanCongCaTrucDTO;
import com.example.staffmanagementsystem.dto.schedule.PhanCongTheoDotDTO;
import com.example.staffmanagementsystem.entity.LichTrucNgay;

import java.util.List;

public interface PhanCongCaTrucService {

    List<PhanCongCaTrucDTO> getByNhanVien(Integer maNV);

    PhanCongCaTrucDTO create(PhanCongCaTrucDTO dto);
    PhanCongCaTrucDTO getById(Integer id);
    List<PhanCongCaTrucDTO> getAll();
    List<LichTrucNgay> taoPhanCongVaSinhLich(PhanCongTheoDotDTO dto);
}
