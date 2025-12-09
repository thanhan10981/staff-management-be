package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.schedule.CaLamViecDTO;
import com.example.staffmanagementsystem.entity.CauHinhCaTruc_Phong;
import com.example.staffmanagementsystem.mapper.ShiftMapper;
import com.example.staffmanagementsystem.repository.CaLamViecRepository;
import com.example.staffmanagementsystem.repository.CauHinhCaTruc_PhongRepository;
import com.example.staffmanagementsystem.service.schedule.ConfigService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
@Service
public class ConfigServiceImpl implements ConfigService {

    private final CaLamViecRepository caRepo;
    private final CauHinhCaTruc_PhongRepository cfgRepo;


    public ConfigServiceImpl(CaLamViecRepository caRepo, CauHinhCaTruc_PhongRepository cfgRepo) {
        this.caRepo = caRepo;
        this.cfgRepo = cfgRepo;
    }


    @Override
    public List<CaLamViecDTO> getAllCa() {
        return caRepo.findAll().stream().map(ShiftMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public CauHinhCaTruc_Phong getCauHinh(Integer maPhong, Integer maCa) {
        return cfgRepo.findById(new com.example.staffmanagementsystem.entity.CauHinhId(maPhong, maCa)).orElse(null);
    }
}
