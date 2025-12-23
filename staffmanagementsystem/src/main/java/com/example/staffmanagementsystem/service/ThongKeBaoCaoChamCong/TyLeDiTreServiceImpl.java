package com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong;


import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.dto.attendancesummary.TyLeDiTrePhongBanChartDTO;
import com.example.staffmanagementsystem.dto.attendancesummary.TyLeDiTrePhongBanChartResponse;
import com.example.staffmanagementsystem.dto.attendancesummary.TyLeDiTreTheoPhongBanDTO;
import com.example.staffmanagementsystem.repository.ThongKeBaoCaoChamCong.TyLeDiTreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TyLeDiTreServiceImpl implements TyLeDiTreService {

    private final TyLeDiTreRepository repository;

    @Override
    public List<TyLeDiTreTheoPhongBanDTO> tyLeDiTre(LocalDate ngayChon, LoaiThongKeNgayCong loai) {
        LocalDate tuNgay;
        LocalDate denNgay;

        switch (loai) {
            case THANG_NAY -> {
                tuNgay = ngayChon.withDayOfMonth(1);
                denNgay = ngayChon;
            }
            case THANG_TRUOC -> {
                YearMonth prevMonth = YearMonth.from(ngayChon).minusMonths(1);
                tuNgay = prevMonth.atDay(1);
                denNgay = prevMonth.atEndOfMonth();
            }
            case QUY_NAY -> {
                int currentQuarter = (ngayChon.getMonthValue() - 1) / 3 + 1;
                int startMonth = (currentQuarter - 1) * 3 + 1;
                tuNgay = LocalDate.of(ngayChon.getYear(), startMonth, 1);
                denNgay = ngayChon;
            }
            case QUY_TRUOC -> {
                int currentQuarter = (ngayChon.getMonthValue() - 1) / 3 + 1;
                int prevQuarter = currentQuarter - 1;
                int year = ngayChon.getYear();
                if (prevQuarter <= 0) {
                    prevQuarter = 4;
                    year -= 1;
                }
                int startMonth = (prevQuarter - 1) * 3 + 1;
                tuNgay = LocalDate.of(year, startMonth, 1);
                denNgay = LocalDate.of(year, startMonth + 2, LocalDate.of(year, startMonth + 2, 1).lengthOfMonth());
            }
            case NAM_NAY -> {
                tuNgay = LocalDate.of(ngayChon.getYear(), 1, 1);
                denNgay = ngayChon;
            }
            case NAM_TRUOC -> {
                tuNgay = LocalDate.of(ngayChon.getYear() - 1, 1, 1);
                denNgay = LocalDate.of(ngayChon.getYear() - 1, 12, 31);
            }
            default -> throw new IllegalArgumentException("Loai range khong hop le");
        }

        return repository.tyLeDiTre(tuNgay, denNgay);
    }

    @Override
    public List<TyLeDiTrePhongBanChartDTO> tyLeDiTreChart(
            LocalDate ngayChon,
            LoaiThongKeNgayCong loai
    ) {
        // 1. Tính range (reuse code cũ)
        LocalDate tuNgay;
        LocalDate denNgay;

        switch (loai) {
            case THANG_NAY -> {
                tuNgay = ngayChon.withDayOfMonth(1);
                denNgay = ngayChon;
            }
            case THANG_TRUOC -> {
                YearMonth prev = YearMonth.from(ngayChon).minusMonths(1);
                tuNgay = prev.atDay(1);
                denNgay = prev.atEndOfMonth();
            }
            case QUY_NAY -> {
                int q = (ngayChon.getMonthValue() - 1) / 3 + 1;
                tuNgay = LocalDate.of(ngayChon.getYear(), (q - 1) * 3 + 1, 1);
                denNgay = ngayChon;
            }
            case QUY_TRUOC -> {
                int q = (ngayChon.getMonthValue() - 1) / 3;
                int year = ngayChon.getYear();
                if (q == 0) {
                    q = 4;
                    year--;
                }
                int startMonth = (q - 1) * 3 + 1;
                tuNgay = LocalDate.of(year, startMonth, 1);
                denNgay = LocalDate.of(year, startMonth + 2,
                        YearMonth.of(year, startMonth + 2).lengthOfMonth());
            }
            case NAM_NAY -> {
                tuNgay = LocalDate.of(ngayChon.getYear(), 1, 1);
                denNgay = ngayChon;
            }
            case NAM_TRUOC -> {
                tuNgay = LocalDate.of(ngayChon.getYear() - 1, 1, 1);
                denNgay = LocalDate.of(ngayChon.getYear() - 1, 12, 31);
            }
            default -> throw new IllegalArgumentException("Range không hợp lệ");
        }

        // 2. Lấy raw data
        List<TyLeDiTreTheoPhongBanDTO> raw =
                repository.tyLeDiTre(tuNgay, denNgay);

        if (raw.isEmpty()) return List.of();

        // 3. Tổng số lần đi trễ
        long totalLate = raw.stream()
                .mapToLong(TyLeDiTreTheoPhongBanDTO::getSoLanDiTre)
                .sum();

        // 4. Quy đổi %
        // 4. Quy đổi %
        return raw.stream()
                .map(r -> (TyLeDiTrePhongBanChartDTO)
                        new TyLeDiTrePhongBanChartResponse(
                                r.getTenPhongBan(),
                                Math.round(
                                        (r.getSoLanDiTre() * 100.0 / totalLate) * 10
                                ) / 10.0
                        )
                )
                .toList();

    }

}
