package com.example.staffmanagementsystem.mapper;

import com.example.staffmanagementsystem.dto.DonNghiPhepDTO;
import com.example.staffmanagementsystem.entity.DonNghiPhep;
import com.example.staffmanagementsystem.entity.NhanVien;
import org.springframework.stereotype.Component;

@Component
public class DonNghiPhepMapper {

    public DonNghiPhepDTO toDto(DonNghiPhep entity) {
        if (entity == null) return null;

        DonNghiPhepDTO dto = new DonNghiPhepDTO();

        // ✅ FIX: map MaDon từ entity.id
        dto.setMaDon(entity.getId());

        dto.setLoaiNghi(entity.getLoaiNghi());
        dto.setNgayBatDau(entity.getNgayBatDau());
        dto.setNgayKetThuc(entity.getNgayKetThuc());
        dto.setLyDo(entity.getLyDo());
        dto.setTrangThai(entity.getTrangThai());

        if (entity.getNhanVien() != null) {
            dto.setMaNhanVien(entity.getNhanVien().getMaNhanVien());
            dto.setTenNhanVien(entity.getNhanVien().getTenNhanVien());
        }

        return dto;
    }


    public DonNghiPhep toEntity(DonNghiPhepDTO dto) {
        if (dto == null) return null;

        DonNghiPhep entity = new DonNghiPhep();

        // ❌ KHÔNG setMaDon vì ID tự tăng
        entity.setLoaiNghi(dto.getLoaiNghi());
        entity.setNgayBatDau(dto.getNgayBatDau());
        entity.setNgayKetThuc(dto.getNgayKetThuc());
        entity.setLyDo(dto.getLyDo());
        entity.setTrangThai(dto.getTrangThai());

        if (dto.getMaNhanVien() != null) {
            NhanVien nv = new NhanVien();
            nv.setMaNhanVien(dto.getMaNhanVien());
            entity.setNhanVien(nv);
        }

        return entity;
    }

}
