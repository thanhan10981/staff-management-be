package com.example.staffmanagementsystem.mapper;

import com.example.staffmanagementsystem.dto.ViTriCongViecDTO;
import com.example.staffmanagementsystem.entity.ViTriCongViec;
import org.springframework.stereotype.Component;

@Component
public class ViTriCongViecMapper {

    public ViTriCongViecDTO toDto(ViTriCongViec vt) {
        return ViTriCongViecDTO.builder()
                .maViTri(vt.getId())
                .tenViTri(vt.getTenViTri())
                .moTa(vt.getMoTa())
                .maPhongBan(vt.getPhongBan() != null ? vt.getPhongBan().getId() : null)
                .build();
    }
}
