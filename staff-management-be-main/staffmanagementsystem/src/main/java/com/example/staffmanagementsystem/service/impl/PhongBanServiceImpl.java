package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.PhongBanDTO;
import com.example.staffmanagementsystem.mapper.PhongBanMapper;
import com.example.staffmanagementsystem.repository.PhongBanRepository;
import com.example.staffmanagementsystem.service.PhongBanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhongBanServiceImpl implements PhongBanService {

    private final PhongBanRepository phongBanRepository;
    private final PhongBanMapper phongBanMapper;

    @Override
    public List<PhongBanDTO> getPhongBanTheoKhoa(int maKhoa) {
        return phongBanRepository.findByKhoa_Id(maKhoa)
                .stream()
                .map(phongBanMapper::toDto)
                .toList();
    }
}
