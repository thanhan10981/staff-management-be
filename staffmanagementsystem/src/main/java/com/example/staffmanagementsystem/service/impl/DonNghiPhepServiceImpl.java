package com.example.staffmanagementsystem.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.staffmanagementsystem.dto.DonNghiPhepDTO;
import com.example.staffmanagementsystem.entity.DonNghiPhep;
import com.example.staffmanagementsystem.mapper.DonNghiPhepMapper;
import com.example.staffmanagementsystem.repository.DonNghiPhepRepository;
import com.example.staffmanagementsystem.service.DonNghiPhepService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DonNghiPhepServiceImpl implements DonNghiPhepService {

    private final DonNghiPhepRepository repo;
    private final DonNghiPhepMapper mapper;

    @Override
    public List<DonNghiPhepDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public DonNghiPhepDTO getById(Integer id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Override
    public DonNghiPhepDTO create(DonNghiPhepDTO dto) {
        DonNghiPhep entity = mapper.toEntity(dto);

        // ✔ trạng thái mặc định đúng nghiệp vụ
        entity.setTrangThai("Chờ duyệt");

        return mapper.toDto(repo.save(entity));
    }

    @Override
    public DonNghiPhepDTO update(Integer id, DonNghiPhepDTO dto) {
        DonNghiPhep entity = repo.findById(id).orElseThrow();

        entity.setLoaiNghi(dto.getLoaiNghi());
        entity.setNgayBatDau(dto.getNgayBatDau());
        entity.setNgayKetThuc(dto.getNgayKetThuc());
        entity.setLyDo(dto.getLyDo());
        entity.setTrangThai(dto.getTrangThai());

        return mapper.toDto(repo.save(entity));
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public List<DonNghiPhepDTO> search(String keyword) {
        return repo.search(keyword)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public DonNghiPhepDTO updateStatus(Integer id, String status, String note) {
        DonNghiPhep entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn"));

        entity.setTrangThai(status);
        

        return mapper.toDto(repo.save(entity));
    }


}
