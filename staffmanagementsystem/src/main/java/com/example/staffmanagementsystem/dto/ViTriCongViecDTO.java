package com.example.staffmanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViTriCongViecDTO {
    private Integer maViTri;
    private String tenViTri;
    private String moTa;
    private Integer maPhongBan;
}
