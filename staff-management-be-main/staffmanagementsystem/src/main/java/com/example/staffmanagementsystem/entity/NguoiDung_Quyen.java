package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NguoiDung_Quyen")
@IdClass(NguoiDungQuyenId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NguoiDung_Quyen {
    @Id
    @Column(name = "MaNguoiDung")
    private Integer maNguoiDung;

    @Id
    @Column(name = "MaQuyen")
    private Integer maQuyen;
}
