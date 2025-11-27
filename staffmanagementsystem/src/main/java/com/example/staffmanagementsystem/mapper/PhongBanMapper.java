package com.example.staffmanagementsystem.mapper;

import com.example.staffmanagementsystem.dto.PhongBanDTO;
import com.example.staffmanagementsystem.entity.PhongBan;
import org.springframework.stereotype.Component;

@Component
public class PhongBanMapper {

    public PhongBanDTO toDto(PhongBan pb) {
        return PhongBanDTO.builder()
                .maPhongBan(pb.getId())
                .tenPhongBan(pb.getTenPhongBan())
                .moTa(pb.getMoTa())
                .maKhoa(pb.getKhoa() != null ? pb.getKhoa().getId() : null)
                .build();
    }
}
