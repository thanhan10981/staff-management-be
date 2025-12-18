package com.example.staffmanagementsystem.mapper;

import com.example.staffmanagementsystem.dto.ViTriCongViecDTO;
import com.example.staffmanagementsystem.entity.ViTriCongViec;
import org.springframework.stereotype.Component;

@Component
public class ViTriCongViecMapper {

    public ViTriCongViecDTO toDto(ViTriCongViec entity) {
        if (entity == null) return null;

        ViTriCongViecDTO dto = new ViTriCongViecDTO();
        dto.setId(entity.getId());
        dto.setTenViTri(entity.getTenViTri());
        dto.setMoTa(entity.getMoTa());

        return dto;
    }
}
