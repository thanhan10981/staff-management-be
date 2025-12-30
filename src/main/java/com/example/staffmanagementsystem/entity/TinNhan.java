package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "TinNhan")
@Data
public class TinNhan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maTinNhan;

    private Integer nguoiGui;
    private Integer nguoiNhan;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String noiDung;

    private LocalDateTime thoiGianGui;

    private String trangThai;
}
