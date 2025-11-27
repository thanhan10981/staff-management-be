package com.example.staffmanagementsystem.mapper;

import com.example.staffmanagementsystem.dto.schedule.ShiftDto;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.entity.CaLamViec;
import com.example.staffmanagementsystem.entity.PhongVatLy;

public class ScheduleMapper {
    public static ShiftDto toShiftDto(LichTrucNgay l) {
        ShiftDto s = new ShiftDto();
        s.setMaLichTruc(l.getMaLichTruc());
        s.setMaNhanVien(l.getMaNhanVien());
        s.setMaCa(l.getMaCa());
        s.setMaPhong(l.getMaPhong());
        s.setTrangThai(l.getTrangThai());
        if (l.getNhanVien() != null) s.setTenNhanVien(l.getNhanVien().getTenNhanVien());
        if (l.getCa() != null) s.setTenCa(l.getCa().getTenCa());
        if (l.getPhong() != null) s.setTenPhong(l.getPhong().getTenPhong());
        return s;
    }
}
