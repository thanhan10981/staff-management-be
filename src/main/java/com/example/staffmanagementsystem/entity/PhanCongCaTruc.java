package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "PhanCongCaTruc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhanCongCaTruc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maPhanCong;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVien nhanVien;

    private Integer maCa;

    @ManyToOne
    @JoinColumn(name = "MaCa", insertable = false, updatable = false)
    private CaLamViec caLamViec;

    private Integer maPhong;

    @ManyToOne
    @JoinColumn(name = "MaPhong", insertable = false, updatable = false)
    private PhongBan phongBan;

    private Integer maKhoa;

    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    private Integer lapLaiHangTuan;
    private String trangThai;
    private String nguoiTao;

    private String ghiChu;
}

