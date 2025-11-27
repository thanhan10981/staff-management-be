package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.ViTriCongViecDTO;
import com.example.staffmanagementsystem.mapper.ViTriCongViecMapper;
import com.example.staffmanagementsystem.repository.ViTriCongViecRepository;
import com.example.staffmanagementsystem.service.ViTriCongViecService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViTriCongViecServiceImpl implements ViTriCongViecService {

    private final ViTriCongViecRepository repo;
    private final ViTriCongViecMapper mapper;

    @Override
    public List<ViTriCongViecDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
