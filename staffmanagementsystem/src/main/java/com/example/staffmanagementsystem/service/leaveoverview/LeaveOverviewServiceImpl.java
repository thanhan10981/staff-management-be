package com.example.staffmanagementsystem.service.leaveoverview;


import com.example.staffmanagementsystem.dto.leaveoverview.LeaveOverviewDTO;
import com.example.staffmanagementsystem.dto.leaveoverview.LeaveOverviewFilterRequest;
import com.example.staffmanagementsystem.repository.leaveoverview.LeaveOverviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveOverviewServiceImpl implements LeaveOverviewService {

    private final LeaveOverviewRepository repository;

    @Override
    public List<LeaveOverviewDTO> getLeaveOverview(LeaveOverviewFilterRequest request) {

        LocalDate now = LocalDate.now();
        LocalDate fromDate;
        LocalDate toDate;

        switch (request.getTimeRange()) {
            case THANG_NAY -> {
                fromDate = now.withDayOfMonth(1);
                toDate = now.withDayOfMonth(now.lengthOfMonth());
            }
            case QUY_NAY -> {
                int quarter = (now.getMonthValue() - 1) / 3;
                fromDate = LocalDate.of(now.getYear(), quarter * 3 + 1, 1);
                toDate = fromDate.plusMonths(3).minusDays(1);
            }
            case NAM_NAY -> {
                fromDate = LocalDate.of(now.getYear(), 1, 1);
                toDate = LocalDate.of(now.getYear(), 12, 31);
            }
            default -> throw new IllegalArgumentException("TimeRange không hợp lệ");
        }

        List<Object[]> rawData = repository.getLeaveOverviewRaw(
                fromDate,
                toDate,
                request.getMaPhongBan(),
                request.getTenPhongBan()
        );

        List<LeaveOverviewDTO> result = new ArrayList<>();

        for (Object[] row : rawData) {
            result.add(LeaveOverviewDTO.builder()
                    .avatar((String) row[0])
                    .tenNhanVien((String) row[1])
                    .email((String) row[2])
                    .tenPhongBan((String) row[3])
                    .tongNghiPhepNam((Integer) row[4])
                    .tongNghiBenh((Integer) row[5])
                    .nghiKhongLuongVuot((Integer) row[6])
                    .tongNgayNghi((Integer) row[7])
                    .soNgayConLai((Integer) row[8])
                    .build());
        }

        return result;
    }
}
