package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.PhongBanDTO;
import com.example.staffmanagementsystem.repository.PhongBanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhongBanServiceImpl implements PhongBanService {

    private final PhongBanRepository repo;

    @Override
    public List<PhongBanDTO> getPhongBanTheoKhoa(int maKhoa) {
        return repo.findByKhoa_Id(maKhoa)
                .stream()
                .map(PhongBanDTO::fromEntity)
                .toList();
    }


}
