package com.example.staffmanagementsystem.service.leaveoverview;



import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.dto.leaveoverview.*;
import com.example.staffmanagementsystem.repository.leaveoverview.DonNghiPhepLRepository;
import com.example.staffmanagementsystem.repository.leaveoverview.LeaveStatsRepository;
import com.example.staffmanagementsystem.repository.leaveoverview.LeaveUnpaidStatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveStatsServiceImpl implements LeaveStatsService {

    private final LeaveStatsRepository repository;

    @Override
    public TotalSickLeaveDaysDTO getTotalSickLeaveDays(
            TotalSickLeaveFilterRequest request) {

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

        Long total = repository.getTotalSickLeaveDays(
                fromDate,
                toDate,
                request.getMaPhongBan(),
                request.getTenPhongBan()
        );

        return new TotalSickLeaveDaysDTO(
                total != null ? total : 0L
        );
    }

    private final LeaveUnpaidStatisticRepository lrepository;

    @Override
    public TotalUnpaidLeaveDaysDTO getTotalUnpaidLeaveDays(
            LeaveUnpaidStatisticFilterRequest request) {

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
                LocalDate prevQ = now.minusMonths(3);
                int q = (prevQ.getMonthValue() - 1) / 3;
                fromDate = LocalDate.of(prevQ.getYear(), q * 3 + 1, 1);
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

        Long total = lrepository.getTotalUnpaidLeaveDays(
                now,
                fromDate,
                toDate,
                request.getMaPhongBan(),
                request.getTenPhongBan()
        );

        return TotalUnpaidLeaveDaysDTO.builder()
                .tongNgayNghiKhongLuong(total != null ? total : 0L)
                .build();
    }

    private final DonNghiPhepLRepository donNghiPhepLRepository;

    @Override
    public List<LeaveTypeDTO> getAllLeaveTypes() {
        return donNghiPhepLRepository.findDistinctLoaiNghi()
                .stream()
                .map(LeaveTypeDTO::new)
                .toList();
    }
}
