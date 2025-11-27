package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.PhanCongCaTrucDTO;
import com.example.staffmanagementsystem.entity.PhanCongCaTruc;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.mapper.PhanCongCaTrucMapper;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.repository.PhanCongCaTrucRepository;
import com.example.staffmanagementsystem.service.PhanCongCaTrucService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhanCongCaTrucServiceImpl implements PhanCongCaTrucService {

    private final PhanCongCaTrucRepository repo;
    private final NhanVienRepository nvRepo;   // <- PHẢI CÓ DÒNG NÀY
    private final PhanCongCaTrucMapper mapper;

    @Override
    public List<PhanCongCaTrucDTO> getByNhanVien(Integer maNV) {
        return repo.findByNhanVien_MaNhanVien(maNV)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PhanCongCaTrucDTO create(PhanCongCaTrucDTO dto) {

        NhanVien nv = nvRepo.findById(dto.getMaNhanVien())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên!"));

        PhanCongCaTruc pc = PhanCongCaTruc.builder()
                .nhanVien(nv)
                .maCa(dto.getMaCa())
                .maPhong(dto.getMaPhong())
                .maKhoa(dto.getMaKhoa())
                .ngayBatDau(dto.getNgayBatDau())
                .ngayKetThuc(dto.getNgayKetThuc())
                .lapLaiHangTuan(dto.getLapLaiHangTuan())
                .trangThai("1")
                .ghiChu(dto.getGhiChu())
                .build();

        repo.save(pc);
        return mapper.toDTO(pc);
    }

    @Override
    public PhanCongCaTrucDTO getById(Integer id) {
        return repo.findById(id).map(mapper::toDTO).orElse(null);
    }

    @Override
    public List<PhanCongCaTrucDTO> getAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }
}
