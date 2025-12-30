package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ChungChiHanhNghe")
public class ChungChi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChungChi")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVien nhanVien;

    @Column(name = "SoChungChi")
    private String soChungChi;

    @Column(name = "NgayCap")
    private LocalDate ngayCap;

    @Column(name = "NoiCap")
    private String noiCap;

    @Column(name = "NgayHetHan")
    private LocalDate ngayHetHan;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name = "TepDinhKem")
    private String tepDinhKem;
}
