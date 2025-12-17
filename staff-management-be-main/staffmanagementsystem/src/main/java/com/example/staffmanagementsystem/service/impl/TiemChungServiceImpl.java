package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.TiemChungDTO;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.entity.TiemChung;
import com.example.staffmanagementsystem.mapper.TiemChungMapper;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.repository.TiemChungRepository;
import com.example.staffmanagementsystem.service.TiemChungService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TiemChungServiceImpl implements TiemChungService {

    private final TiemChungRepository repo;
    private final NhanVienRepository nvRepo;
    private final TiemChungMapper mapper;

    @Override
    public List<TiemChungDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<TiemChungDTO> getByNhanVien(Integer id) {
        return repo.findByNhanVien_MaNhanVien(id)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public TiemChungDTO create(TiemChungDTO dto) {

        NhanVien nv = nvRepo.findById(dto.getMaNhanVien())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        TiemChung entity = mapper.toEntity(dto, nv);

        return mapper.toDto(repo.save(entity));
    }
}
