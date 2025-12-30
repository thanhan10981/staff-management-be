package com.example.staffmanagementsystem.dto;

import java.time.LocalDate;

public class YeuCauDoiCaDTO {

    private Integer maYeuCau;
    private Integer nguoiGui;
    private Integer nguoiNhan;
    private Integer maCa;
    private LocalDate ngayTruc;
    private String lyDo;
    private String trangThai;

    // ===== FIELD PHỤC VỤ FE =====
    private String tenNguoiGui;
    private String tenNguoiNhan;
    private String tenCa;

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

    public String getTenNguoiGui() { return tenNguoiGui; }
    public void setTenNguoiGui(String tenNguoiGui) { this.tenNguoiGui = tenNguoiGui; }

    public String getTenNguoiNhan() { return tenNguoiNhan; }
    public void setTenNguoiNhan(String tenNguoiNhan) { this.tenNguoiNhan = tenNguoiNhan; }

    public String getTenCa() { return tenCa; }
    public void setTenCa(String tenCa) { this.tenCa = tenCa; }
}
