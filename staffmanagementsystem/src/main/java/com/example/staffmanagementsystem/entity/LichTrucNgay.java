package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "LichTrucNgay")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LichTrucNgay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maLichTruc;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVien nhanVien;

    private Integer maCa;
    private Integer maPhong;

    private LocalDate ngayTruc;

    private String trangThai;
    private String ghiChu;

    // ==========================
    //  THÊM CHO MAPPER
    // ==========================

    // 1) Getter trả về ID nhân viên (mapper yêu cầu)
    public Integer getMaNhanVien() {
        return nhanVien != null ? nhanVien.getMaNhanVien() : null;
    }

    // 2) Quan hệ ManyToOne với CaLamViec
    @ManyToOne
    @JoinColumn(name = "MaCa", insertable = false, updatable = false)
    private CaLamViec ca;

    // 3) Quan hệ ManyToOne với PhongVatLy
    @ManyToOne
    @JoinColumn(name = "MaPhong", insertable = false, updatable = false)
    private PhongVatLy phong;
}
