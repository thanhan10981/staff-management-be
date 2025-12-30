package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PhanQuyen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhanQuyen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaQuyen")
    private Integer maQuyen;

    @Column(name = "TenQuyen")
    private String tenQuyen;

    @Column(name = "MoTa")
    private String moTa;
}
