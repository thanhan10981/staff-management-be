package com.example.staffmanagementsystem.mapper;

import com.example.staffmanagementsystem.dto.ChungChiDTO;
import com.example.staffmanagementsystem.entity.ChungChi;
import org.springframework.stereotype.Component;

@Component
public class ChungChiMapper {

    public ChungChiDTO toDto(ChungChi cc) {
        if (cc == null) return null;

        return ChungChiDTO.builder()
                .id(cc.getId())
                .maNhanVien(cc.getNhanVien() != null ? cc.getNhanVien().getMaNhanVien() : null)
                .tenNhanVien(cc.getNhanVien() != null ? cc.getNhanVien().getTenNhanVien() : null)
                .soChungChi(cc.getSoChungChi())
                .ngayCap(cc.getNgayCap())
                .noiCap(cc.getNoiCap())
                .ngayHetHan(cc.getNgayHetHan())
                .trangThai(cc.getTrangThai())
                .tepDinhKem(cc.getTepDinhKem())
                .build();
    }
}
