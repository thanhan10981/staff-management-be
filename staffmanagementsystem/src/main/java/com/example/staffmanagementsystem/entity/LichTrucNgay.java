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

    public Integer getMaLichTruc() {
        return maLichTruc;
    }

    // NV
    @ManyToOne
    @JoinColumn(name = "MaNhanVien")
    private NhanVien nhanVien;

    // Ca + Phòng (ID)
    @Column(name = "MaCa")
    private Integer maCa;

    @Column(name = "MaPhong")
    private Integer maPhong;

    private LocalDate ngayTruc;
    private String trangThai;
    private String ghiChu;

    // ==========================
    //  Quan hệ ManyToOne
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaCa", insertable = false, updatable = false)
    private CaLamViec caLamViec;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPhong", insertable = false, updatable = false)
    private PhongVatLy phongVatLy;

    // ==========================
    //  SUPPORT CHO MAPPER
    // ==========================

    public Integer getMaNhanVien() {
        return nhanVien != null ? nhanVien.getMaNhanVien() : null;


    }

}

