package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.ChungChiDTO;
import com.example.staffmanagementsystem.entity.ChungChi;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.mapper.ChungChiMapper;
import com.example.staffmanagementsystem.repository.ChungChiRepository;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.service.ChungChiService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChungChiServiceImpl implements ChungChiService {

    private final ChungChiRepository repo;
    private final NhanVienRepository nvRepo;
    private final ChungChiMapper mapper;

    @Override
    public List<ChungChiDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<ChungChiDTO> getByNhanVien(Integer maNhanVien) {
        return repo.findByNhanVien_MaNhanVien(maNhanVien)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ChungChiDTO create(ChungChiDTO dto) {

        ChungChi cc = new ChungChi();
        NhanVien nv = nvRepo.findById(dto.getMaNhanVien()).orElse(null);

        cc.setNhanVien(nv);
        cc.setSoChungChi(dto.getSoChungChi());
        cc.setNgayCap(dto.getNgayCap());
        cc.setNoiCap(dto.getNoiCap());
        cc.setNgayHetHan(dto.getNgayHetHan());
        cc.setTrangThai(dto.getTrangThai());
        cc.setTepDinhKem(dto.getTepDinhKem());

        return mapper.toDto(repo.save(cc));
    }

    @Override
    public ChungChiDTO update(Integer id, ChungChiDTO dto) {

        ChungChi cc = repo.findById(id).orElseThrow();
        NhanVien nv = nvRepo.findById(dto.getMaNhanVien()).orElse(null);

        cc.setNhanVien(nv);
        cc.setSoChungChi(dto.getSoChungChi());
        cc.setNgayCap(dto.getNgayCap());
        cc.setNoiCap(dto.getNoiCap());
        cc.setNgayHetHan(dto.getNgayHetHan());
        cc.setTrangThai(dto.getTrangThai());
        cc.setTepDinhKem(dto.getTepDinhKem());

        return mapper.toDto(repo.save(cc));
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
