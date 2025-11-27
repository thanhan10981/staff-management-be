package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Khoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Khoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKhoa")
    private Integer id;

    @Column(name = "TenKhoa")
    private String tenKhoa;

    @Column(name = "MoTa")
    private String moTa;

    // 🔥 Alias – để mapper dùng getMaKhoa()
    public Integer getMaKhoa() {
        return this.id;
    }

    public void setMaKhoa(Integer maKhoa) {
        this.id = maKhoa;
    }
}
