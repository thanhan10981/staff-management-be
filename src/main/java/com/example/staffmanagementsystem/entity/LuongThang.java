package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;  // JPA cho Spring Boot 3
import lombok.*;             // Lombok
import java.time.LocalDate;  // LocalDate

@Entity
@Table(name = "LuongThang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LuongThang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maLuong;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVien nhanVien;
    private Integer thang;
    private Integer nam;

    @Column(name = "LuongCoBan")
    private Long luongCoBan;

    @Column(name = "PhuCapCoDinh")
    private Long phuCapCoDinh;

    @Column(nullable = true)
    private Long phuCapKhac = 0L;

    @Column(nullable = true)
    private Long phuCapTrucCa = 0L;


    @Column(name = "TongThuNhap")
    private Long tongThuNhap;

    private Long bhxh;
    private Long thueTncn;
    private Long thucLanh;

    @Column(name = "NgayTinh")
    private LocalDate ngayTinh;
}
