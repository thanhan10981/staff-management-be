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
    private Integer maPhong;
    private Integer maKhoa;

    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    private Integer lapLaiHangTuan;   // CHỈNH
    private String trangThai;         // CHỈNH
    private String nguoiTao;         // THÊM

    private String ghiChu;
}
