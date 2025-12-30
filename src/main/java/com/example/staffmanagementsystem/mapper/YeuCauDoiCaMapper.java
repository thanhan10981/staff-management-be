package com.example.staffmanagementsystem.mapper;

import com.example.staffmanagementsystem.dto.YeuCauDoiCaDTO;
import com.example.staffmanagementsystem.entity.YeuCauDoiCa;

public class YeuCauDoiCaMapper {

    public static YeuCauDoiCaDTO toDTO(YeuCauDoiCa e) {
        if (e == null) return null;

        YeuCauDoiCaDTO dto = new YeuCauDoiCaDTO();
        dto.setMaYeuCau(e.getMaYeuCau());
        dto.setNguoiGui(e.getNguoiGui());
        dto.setNguoiNhan(e.getNguoiNhan());
        dto.setMaCa(e.getMaCa());
        dto.setNgayTruc(e.getNgayTruc());
        dto.setLyDo(e.getLyDo());
        dto.setTrangThai(e.getTrangThai());

        // tạm map tên để FE hiển thị (sau này JOIN)
        dto.setTenNguoiGui("NV " + e.getNguoiGui());
        dto.setTenNguoiNhan("NV " + e.getNguoiNhan());
        dto.setTenCa("Ca " + e.getMaCa());

        return dto;
    }
}
