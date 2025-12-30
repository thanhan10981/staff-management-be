package com.example.staffmanagementsystem.dto;

import java.time.LocalDate;

public class YeuCauDoiCaCreateDTO {


    private Integer nguoiNhan;
    private Integer maCa;
    private LocalDate ngayTruc;
    private String lyDo;

    public Integer getNguoiNhan() {
        return nguoiNhan;
    }

    public void setNguoiNhan(Integer nguoiNhan) {
        this.nguoiNhan = nguoiNhan;
    }

    public Integer getMaCa() {
        return maCa;
    }

    public void setMaCa(Integer maCa) {
        this.maCa = maCa;
    }

    public LocalDate getNgayTruc() {
        return ngayTruc;
    }

    public void setNgayTruc(LocalDate ngayTruc) {
        this.ngayTruc = ngayTruc;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }
}
