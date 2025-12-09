package com.example.staffmanagementsystem.service.impl;


import com.example.staffmanagementsystem.dto.PhongVatLyDTO;
import com.example.staffmanagementsystem.repository.PhongVatLyRepository;
import com.example.staffmanagementsystem.service.PhongVLService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PhongVLServiceImpl implements PhongVLService {

    private final PhongVatLyRepository repo;

    @Override
    public List<PhongVatLyDTO> getPhongTheoKhoa(int maKhoa) {
        return repo.findByKhoa_Id(maKhoa)
                .stream()
                .map(PhongVatLyDTO::fromEntity)
                .toList();
    }

}

