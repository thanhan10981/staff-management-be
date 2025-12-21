package com.example.staffmanagementsystem.service.leaveoverview;

import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.dto.leaveoverview.LeaveTotalStatisticDTO;
import com.example.staffmanagementsystem.dto.leaveoverview.LeaveTotalStatisticFilterRequest;
import com.example.staffmanagementsystem.repository.leaveoverview.LeaveTotalStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LeaveTotalStatisticServiceImpl
        implements LeaveTotalStatisticService {

    private final LeaveTotalStatisticRepository repository;

    @Override
    public LeaveTotalStatisticDTO getTotalLeaveStatistic(
            LeaveTotalStatisticFilterRequest request) {

        LocalDate now = LocalDate.now();
        LocalDate fromDate;
        LocalDate toDate;

        LoaiThongKeNgayCong range =
                request.getTimeRange() != null
                        ? request.getTimeRange()
                        : LoaiThongKeNgayCong.THANG_NAY;

        switch (range) {
            case THANG_NAY -> {
                fromDate = now.withDayOfMonth(1);
                toDate = now.withDayOfMonth(now.lengthOfMonth());
            }
            case THANG_TRUOC -> {
                LocalDate prev = now.minusMonths(1);
                fromDate = prev.withDayOfMonth(1);
                toDate = prev.withDayOfMonth(prev.lengthOfMonth());
            }
            case QUY_NAY -> {
                int q = (now.getMonthValue() - 1) / 3;
                fromDate = LocalDate.of(now.getYear(), q * 3 + 1, 1);
                toDate = fromDate.plusMonths(3).minusDays(1);
            }
            case QUY_TRUOC -> {
                LocalDate prevQuarter = now.minusMonths(3);
                int q = (prevQuarter.getMonthValue() - 1) / 3;
                fromDate = LocalDate.of(prevQuarter.getYear(), q * 3 + 1, 1);
                toDate = fromDate.plusMonths(3).minusDays(1);
            }
            case NAM_NAY -> {
                fromDate = LocalDate.of(now.getYear(), 1, 1);
                toDate = LocalDate.of(now.getYear(), 12, 31);
            }
            case NAM_TRUOC -> {
                int y = now.getYear() - 1;
                fromDate = LocalDate.of(y, 1, 1);
                toDate = LocalDate.of(y, 12, 31);
            }
            default -> throw new IllegalArgumentException("TimeRange không hợp lệ");
        }

        Integer total = repository.getTongNgayNghiPhepNam(
                fromDate,
                toDate,
                request.getMaPhongBan(),
                request.getTenPhongBan()
        );

        return LeaveTotalStatisticDTO.builder()
                .tongNgayNghiPhepNam(total != null ? total : 0)
                .build();
    }
}
