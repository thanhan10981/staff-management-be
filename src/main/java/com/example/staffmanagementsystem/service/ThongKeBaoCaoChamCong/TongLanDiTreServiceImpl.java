package com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong;


import com.example.staffmanagementsystem.dto.attendancesummary.TongLanDiTreDTO;
import com.example.staffmanagementsystem.repository.ThongKeBaoCaoChamCong.TongLanDiTreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class TongLanDiTreServiceImpl implements TongLanDiTreService {

    private final TongLanDiTreRepository repository;

    @Override
    public TongLanDiTreDTO tinhTongLanDiTre(
            LocalDate ngayChon,
            String loai,
            Integer maPhongBan,
            Integer maViTri
    ) {
        LocalDate tuNgay;
        LocalDate denNgay;

        switch (loai) {
            case "THANG_NAY" -> {
                tuNgay = ngayChon.withDayOfMonth(1);
                denNgay = ngayChon.with(TemporalAdjusters.lastDayOfMonth());
            }
            case "THANG_TRUOC" -> {
                LocalDate thangTruoc = ngayChon.minusMonths(1);
                tuNgay = thangTruoc.withDayOfMonth(1);
                denNgay = thangTruoc.with(TemporalAdjusters.lastDayOfMonth());
            }
            case "QUY_NAY" -> {
                int quy = (ngayChon.getMonthValue() - 1) / 3;
                tuNgay = LocalDate.of(ngayChon.getYear(), quy * 3 + 1, 1);
                denNgay = tuNgay.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
            }
            case "QUY_TRUOC" -> {
                LocalDate quyTruoc = ngayChon.minusMonths(3);
                int quy = (quyTruoc.getMonthValue() - 1) / 3;
                tuNgay = LocalDate.of(quyTruoc.getYear(), quy * 3 + 1, 1);
                denNgay = tuNgay.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
            }
            case "NAM_NAY" -> {
                tuNgay = ngayChon.withDayOfYear(1);
                denNgay = ngayChon.with(TemporalAdjusters.lastDayOfYear());
            }
            case "NAM_TRUOC" -> {
                LocalDate namTruoc = ngayChon.minusYears(1);
                tuNgay = namTruoc.withDayOfYear(1);
                denNgay = namTruoc.with(TemporalAdjusters.lastDayOfYear());
            }
            default -> throw new IllegalArgumentException("Loại thống kê không hợp lệ");
        }

        return repository.tinhTongLanDiTre(
                tuNgay,
                denNgay,
                maPhongBan,
                maViTri
        );
    }
}
