package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.attendancesummary.ChiTietChamCongDTO;
import com.example.staffmanagementsystem.repository.ChiTietChamCongRepository;
import com.example.staffmanagementsystem.service.ChiTietChamCongService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChiTietChamCongServiceImpl implements ChiTietChamCongService {

    private final ChiTietChamCongRepository repository;

    public ChiTietChamCongServiceImpl(ChiTietChamCongRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ChiTietChamCongDTO> chiTietChamCong(
            LocalDate tuNgay,
            LocalDate denNgay,
            Integer maPhongBan,
            Integer maViTri
    ) {
        return repository.chiTietChamCong(
                tuNgay,
                denNgay,
                maPhongBan,
                maViTri
        );
    }
}
