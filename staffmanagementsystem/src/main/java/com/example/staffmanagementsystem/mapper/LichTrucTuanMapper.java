package com.example.staffmanagementsystem.mapper;

import com.example.staffmanagementsystem.dto.LichTrucTuanDTO;
import com.example.staffmanagementsystem.entity.NhanVien;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;


@Component

public class LichTrucTuanMapper {

    public LichTrucTuanDTO toDTO(
            NhanVien nv,
            Map<LocalDate, String> lichTheoNgay
    ) {

        if (nv == null) return null;

        return LichTrucTuanDTO.builder()
                .maNhanVien(nv.getMaNhanVien())
                .hoTen(nv.getTenNhanVien())

                // 👇 PHÒNG BAN
                .maPhongBan(
                        nv.getPhongBan() != null
                                ? nv.getPhongBan().getId()   // ✅ FIX
                                : null
                )
                .tenPhong(
                        nv.getPhongBan() != null
                                ? nv.getPhongBan().getTenPhongBan()
                                : null
                )

                // 👇 KHOA
                .maKhoa(
                        nv.getPhongBan() != null
                                && nv.getPhongBan().getKhoa() != null
                                ? nv.getPhongBan().getKhoa().getId()
                                : null
                )

                // 👇 VỊ TRÍ
                .maViTri(
                        nv.getViTriCongViec() != null
                                ? nv.getViTriCongViec().getId()  // ✅ FIX
                                : null
                )

                // 👇 LỊCH TRONG TUẦN
                .lichTheoNgay(lichTheoNgay)

                .build();
    }
}

