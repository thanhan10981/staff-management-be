package com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong;

import com.example.staffmanagementsystem.dto.attendancesummary.TongNghiKhongPhepDTO;
import com.example.staffmanagementsystem.repository.ThongKeBaoCaoChamCong.TongNghiKhongPhepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
public class TongNghiKhongPhepServiceImpl implements TongNghiKhongPhepService {

    private final TongNghiKhongPhepRepository repository;

    @Override
    public TongNghiKhongPhepDTO tinhTongNghiKhongPhep(
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
                LocalDate t = ngayChon.minusMonths(1);
                tuNgay = t.withDayOfMonth(1);
                denNgay = t.with(TemporalAdjusters.lastDayOfMonth());
            }
            case "QUY_NAY" -> {
                int q = (ngayChon.getMonthValue() - 1) / 3;
                tuNgay = LocalDate.of(ngayChon.getYear(), q * 3 + 1, 1);
                denNgay = tuNgay.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
            }
            case "QUY_TRUOC" -> {
                LocalDate t = ngayChon.minusMonths(3);
                int q = (t.getMonthValue() - 1) / 3;
                tuNgay = LocalDate.of(t.getYear(), q * 3 + 1, 1);
                denNgay = tuNgay.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
            }
            case "NAM_NAY" -> {
                tuNgay = ngayChon.withDayOfYear(1);
                denNgay = ngayChon.with(TemporalAdjusters.lastDayOfYear());
            }
            case "NAM_TRUOC" -> {
                LocalDate t = ngayChon.minusYears(1);
                tuNgay = t.withDayOfYear(1);
                denNgay = t.with(TemporalAdjusters.lastDayOfYear());
            }
            default -> throw new IllegalArgumentException("Loại thống kê không hợp lệ");
        }

        return repository.tinhTongNghiKhongPhep(
                tuNgay,
                denNgay,
                maPhongBan,
                maViTri
        );
    }
}
