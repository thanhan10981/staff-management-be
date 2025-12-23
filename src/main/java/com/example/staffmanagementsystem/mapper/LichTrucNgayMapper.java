package com.example.staffmanagementsystem.mapper;

import com.example.staffmanagementsystem.dto.LichTrucNgayDTO;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import org.springframework.stereotype.Component;

@Component
public class LichTrucNgayMapper {

    public LichTrucNgayDTO toDTO(LichTrucNgay e) {

        if (e == null) return null;

        return LichTrucNgayDTO.builder()
                .maLichTruc(e.getMaLichTruc())
                .maNhanVien(e.getMaNhanVien())
                .hoTen(e.getNhanVien() != null ? e.getNhanVien().getTenNhanVien() : null)
                .maCa(e.getMaCa())
                .maPhong(e.getMaPhong())
                .ngayTruc(e.getNgayTruc())
                .trangThai(e.getTrangThai())
                .ghiChu(e.getGhiChu())
                .build();
    }
}
