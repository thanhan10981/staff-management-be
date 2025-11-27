package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.LichTrucNgayDTO;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.entity.PhanCongCaTruc;
import com.example.staffmanagementsystem.mapper.LichTrucNgayMapper;
import com.example.staffmanagementsystem.repository.*;
import com.example.staffmanagementsystem.service.LichTrucService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LichTrucServiceImpl implements LichTrucService {

    private final PhanCongCaTrucRepository pcRepo;
    private final LichTrucNgayRepository lichRepo;
    private final NhanVienRepository nvRepo;
    private final LichTrucNgayMapper mapper;

    @Override
    public void taoPhanCongTuan(Integer maNV, Integer maPhong, Integer maKhoa, LocalDate start) {

        LocalDate end = start.plusDays(6);
        NhanVien nv = nvRepo.findById(maNV).orElseThrow();

        PhanCongCaTruc pc = PhanCongCaTruc.builder()
                .nhanVien(nv)
                .maCa(0)
                .maPhong(maPhong)
                .maKhoa(maKhoa)
                .ngayBatDau(start)
                .ngayKetThuc(end)
                .lapLaiHangTuan(1)
                .trangThai("1")
                .ghiChu("Phân công trực tuần: " + start)
                .build();

        pcRepo.save(pc);

        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            int thu = d.getDayOfWeek().getValue();
            if (thu >= 1 && thu <= 5) {
                taoLichNgay(maNV, maPhong, 1, d);
                taoLichNgay(maNV, maPhong, 2, d);
            }
        }
    }

    private void taoLichNgay(Integer maNV, Integer maPhong, Integer maCa, LocalDate ngay) {
        LichTrucNgay e = LichTrucNgay.builder()
                .nhanVien(nvRepo.findById(maNV).orElseThrow())
                .maCa(maCa)
                .maPhong(maPhong)
                .ngayTruc(ngay)
                .trangThai("Đang làm việc")
                .ghiChu("Tạo tự động theo phân công")
                .build();

        lichRepo.save(e);
    }

    @Override
    public List<LichTrucNgayDTO> getLichTheoTuan(Integer maNV, LocalDate start, LocalDate end) {
        return lichRepo.findByNhanVien_MaNhanVienAndNgayTrucBetween(maNV, start, end)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
