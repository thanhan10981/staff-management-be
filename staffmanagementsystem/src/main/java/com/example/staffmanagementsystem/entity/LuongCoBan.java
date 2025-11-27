package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;   // JPA cho Spring Boot 3
import lombok.*;                // Lombok

@Entity
@Table(name = "LuongCoBan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LuongCoBan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maLoaiLuong;

    @Column(name = "TenLoai")
    private String tenLoai;

    @Column(name = "LuongCoBan")
    private Long luongCoBan;

    @Column(name = "PhuCapNghe")
    private Long phuCapNghe;

    @Column(name = "PhuCapDocHai")
    private Long phuCapDocHai;

    @Column(name = "PhuCapKhac")
    private Long phuCapKhac;
}
