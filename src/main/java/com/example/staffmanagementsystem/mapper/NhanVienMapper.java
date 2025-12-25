package com.example.staffmanagementsystem.mapper;

import com.example.staffmanagementsystem.dto.NhanVienDTO;
import com.example.staffmanagementsystem.entity.NhanVien;
import org.springframework.stereotype.Component;

@Component
public class NhanVienMapper {

    public NhanVienDTO toDto(NhanVien nv) {

        if (nv == null) return null;

        return NhanVienDTO.builder()
                .maNhanVien(nv.getMaNhanVien())
                .tenNhanVien(nv.getTenNhanVien())
                .ngaySinh(nv.getNgaySinh())
                .gioiTinh(nv.getGioiTinh())
                .sdt(nv.getSdt())
                .email(nv.getEmail())
                .trangThai(nv.getTrangThai())

                // ======== MÃ ==========
                .maViTri(nv.getViTriCongViec() != null
                        ? nv.getViTriCongViec().getId()
                        : null)

                .maPhongBan(nv.getPhongBan() != null
                        ? nv.getPhongBan().getId()
                        : null)

                .maKhoa(nv.getKhoa() != null
                        ? nv.getKhoa().getId()
                        : null)

                // ========= TÊN ==========
                .tenViTri(nv.getViTriCongViec() != null
                        ? nv.getViTriCongViec().getTenViTri()
                        : null)

                .tenPhongBan(nv.getPhongBan() != null
                        ? nv.getPhongBan().getTenPhongBan()
                        : null)

                .tenKhoa(nv.getKhoa() != null
                        ? nv.getKhoa().getTenKhoa()
                        : null)
                .cccd(nv.getCccd())
                .ngayVaoLam(nv.getNgayVaoLam())
                .trinhDoChuyenMon(nv.getTrinhDoChuyenMon())
                .lienHeKhanCap(nv.getLienHeKhanCap())
                .sdtLienHeKhanCap(nv.getSdtLienHeKhanCap())
                .anhDaiDien(nv.getAnhDaiDien())
                .hopDongFile(nv.getHopDongFile())
                .build();
    }
}
