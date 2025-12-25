package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "CaLamViec")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaLamViec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCa")
    private Integer maCa;

    @Column(name = "TenCa")
    private String tenCa;

    @Column(name = "GioBatDau")
    private LocalTime gioBatDau;

    @Column(name = "GioKetThuc")
    private LocalTime gioKetThuc;

    @Column(name = "PhuCap")
    private BigDecimal phuCap;
}
