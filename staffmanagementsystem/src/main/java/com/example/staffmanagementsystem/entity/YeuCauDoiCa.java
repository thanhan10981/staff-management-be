package com.example.staffmanagementsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "YeuCauDoiCa")
public class YeuCauDoiCa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaYeuCau")
    private Integer maYeuCau;

    @Column(name = "NguoiGui")
    private Integer nguoiGui;

    @Column(name = "NguoiNhan")
    private Integer nguoiNhan;

    @Column(name = "MaCa")
    private Integer maCa;

    @Column(name = "NgayTruc")
    private LocalDate ngayTruc;

    @Column(name = "LyDo")
    private String lyDo;

    @Column(name = "TrangThai")
    private String trangThai;

    // ===== GETTER SETTER =====

    public Integer getMaYeuCau() { return maYeuCau; }
    public void setMaYeuCau(Integer maYeuCau) { this.maYeuCau = maYeuCau; }

    public Integer getNguoiGui() { return nguoiGui; }
    public void setNguoiGui(Integer nguoiGui) { this.nguoiGui = nguoiGui; }

    public Integer getNguoiNhan() { return nguoiNhan; }
    public void setNguoiNhan(Integer nguoiNhan) { this.nguoiNhan = nguoiNhan; }

    public Integer getMaCa() { return maCa; }
    public void setMaCa(Integer maCa) { this.maCa = maCa; }

    public LocalDate getNgayTruc() { return ngayTruc; }
    public void setNgayTruc(LocalDate ngayTruc) { this.ngayTruc = ngayTruc; }

    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
