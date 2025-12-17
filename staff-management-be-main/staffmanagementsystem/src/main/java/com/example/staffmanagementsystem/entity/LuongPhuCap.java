package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;   // JPA cho Spring Boot 3
import lombok.*;                // Lombok

@Entity
@Table(name = "LuongPhuCap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LuongPhuCap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maPhuCap;

    @ManyToOne
    @JoinColumn(name = "MaLuong")
    private LuongThang luongThang;

    @Column(name = "LoaiPhuCap")
    private String loaiPhuCap;

    @Column(name = "SoTien")
    private Long soTien;
}
