package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.ViTriCongViecDTO;
import com.example.staffmanagementsystem.mapper.ViTriCongViecMapper;
import com.example.staffmanagementsystem.repository.ViTriCongViecRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViTriCongViecService {

    private final ViTriCongViecRepository viTriCongViecRepository;
    private final ViTriCongViecMapper viTriCongViecMapper;

    public List<ViTriCongViecDTO> getAll() {
        return viTriCongViecRepository.findAll()
                .stream()
                .map(viTriCongViecMapper::toDto)
                .toList();
    }
}
