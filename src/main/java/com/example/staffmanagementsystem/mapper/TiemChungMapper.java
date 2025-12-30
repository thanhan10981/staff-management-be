package com.example.staffmanagementsystem.mapper;

import com.example.staffmanagementsystem.dto.TiemChungDTO;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.entity.TiemChung;
import org.springframework.stereotype.Component;

@Component
public class TiemChungMapper {

    public TiemChungDTO toDto(TiemChung e) {
        return TiemChungDTO.builder()
                .maTiemChung(e.getMaTiemChung())
                .maNhanVien(e.getNhanVien().getMaNhanVien())
                .tenNhanVien(e.getNhanVien().getTenNhanVien())
                .loai(e.getLoai())
                .ngayTiem(e.getNgayTiem())
                .ketQua(e.getKetQua())
                .ghiChu(e.getGhiChu())
                .build();
    }

    public TiemChung toEntity(TiemChungDTO dto, NhanVien nv) {
        return TiemChung.builder()
                .maTiemChung(dto.getMaTiemChung())
                .nhanVien(nv)
                .loai(dto.getLoai())
                .ngayTiem(dto.getNgayTiem())
                .ketQua(dto.getKetQua())
                .ghiChu(dto.getGhiChu())
                .build();
    }
}
