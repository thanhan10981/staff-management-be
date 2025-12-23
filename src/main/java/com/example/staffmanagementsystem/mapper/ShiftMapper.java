package com.example.staffmanagementsystem.mapper;


import com.example.staffmanagementsystem.dto.*;
import com.example.staffmanagementsystem.entity.*;
import com.example.staffmanagementsystem.dto.schedule.*;


public class ShiftMapper {


    public static NhanVienDTO toDto(NhanVien n) {
        if (n == null) return null;
        NhanVienDTO d = new NhanVienDTO();
        d.setMaNhanVien(n.getMaNhanVien());
        d.setTenNhanVien(n.getTenNhanVien());
        d.setEmail(n.getEmail());
        d.setMaKhoa(n.getKhoa() != null ? n.getKhoa().getMaKhoa() : null);
        d.setMaPhongBan(n.getPhongBan() != null ? n.getPhongBan().getId() : null);
        d.setMaViTri(n.getViTriCongViec() != null ? n.getViTriCongViec().getId() : null);
        return d;
    }


    public static CaLamViecDTO toDto(CaLamViec c) {
        if (c == null) return null;
        CaLamViecDTO d = new CaLamViecDTO();
        d.setMaCa(c.getMaCa());
        d.setTenCa(c.getTenCa());
        d.setGioBatDau(c.getGioBatDau());
        d.setGioKetThuc(c.getGioKetThuc());
        d.setPhuCap(c.getPhuCap());
        return d;
    }


    public static LichTrucNgayDTO toDto(LichTrucNgay l) {
        if (l == null) return null;
        LichTrucNgayDTO d = new LichTrucNgayDTO();
        d.setMaLichTruc(l.getMaLichTruc());
        d.setMaNhanVien(l.getMaNhanVien());
        d.setHoTen(l.getNhanVien() != null ? l.getNhanVien().getTenNhanVien() : null);
        d.setMaCa(l.getMaCa());
        d.setMaPhong(l.getMaPhong());
        d.setNgayTruc(l.getNgayTruc());
        d.setTrangThai(l.getTrangThai());
        d.setGhiChu(l.getGhiChu());
        return d;
    }



}