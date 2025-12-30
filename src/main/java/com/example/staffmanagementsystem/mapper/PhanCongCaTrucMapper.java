package com.example.staffmanagementsystem.mapper;

import com.example.staffmanagementsystem.dto.PhanCongCaTrucDTO;
import com.example.staffmanagementsystem.entity.PhanCongCaTruc;
import org.springframework.stereotype.Component;

@Component
public class PhanCongCaTrucMapper {

    public PhanCongCaTrucDTO toDTO(PhanCongCaTruc e) {
        if (e == null) return null;

        return PhanCongCaTrucDTO.builder()
                .maPhanCong(e.getMaPhanCong())
                .maNhanVien(e.getNhanVien().getMaNhanVien())
                .hoTen(e.getNhanVien().getTenNhanVien())
                .maCa(e.getMaCa())
                .maPhong(e.getMaPhong())
                .maKhoa(e.getMaKhoa())
                .ngayBatDau(e.getNgayBatDau())
                .ngayKetThuc(e.getNgayKetThuc())
                .lapLaiHangTuan(e.getLapLaiHangTuan())
                .trangThai(e.getTrangThai())
                .ghiChu(e.getGhiChu())
                .build();
    }
}
