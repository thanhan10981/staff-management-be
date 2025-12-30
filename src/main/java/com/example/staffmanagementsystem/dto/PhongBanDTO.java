package com.example.staffmanagementsystem.dto;


import com.example.staffmanagementsystem.entity.PhongBan;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhongBanDTO {

    private Integer id;
    private String tenPhongBan;
    private String moTa;

    private Integer maKhoa;
    private String tenKhoa;

    public static PhongBanDTO fromEntity(PhongBan pb) {
        if (pb == null) return null;

        return PhongBanDTO.builder()
                .id(pb.getId())
                .tenPhongBan(pb.getTenPhongBan())
                .moTa(pb.getMoTa())
                .maKhoa(pb.getKhoa() != null ? pb.getKhoa().getId() : null)
                .tenKhoa(pb.getKhoa() != null ? pb.getKhoa().getTenKhoa() : null)
                .build();
    }
}

