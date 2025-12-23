package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "CauHinhCaTruc")
@Getter
@Setter
public class CauHinhCaTruc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double heSoNgayThuong;
    private Double heSoCuoiTuan;
    private Double heSoNgayLe;

    private LocalDateTime updatedAt;
}
