package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.ViTriCongViecDTO;
import com.example.staffmanagementsystem.entity.ViTriCongViec;
import com.example.staffmanagementsystem.repository.ViTriCongViecRepository;
import com.example.staffmanagementsystem.service.ViTriCongViecService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViTriCongViecServiceImpl implements ViTriCongViecService {

    private final ViTriCongViecRepository repository;

    @Override
    public List<ViTriCongViec> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ViTriCongViecDTO> getViTriTheoPhongBan(Integer maPhongBan) {
        return repository.findByMaPhongBan(maPhongBan);
    }
}
