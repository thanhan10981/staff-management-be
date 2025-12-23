package com.example.staffmanagementsystem.service.leaveoverview;

import com.example.staffmanagementsystem.dto.leaveoverview.LeaveExportRequest;
import com.example.staffmanagementsystem.repository.leaveoverview.LeaveReportExportRepository;
import com.example.staffmanagementsystem.utils.PDFExportUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveReportExportPDFServiceImpl implements LeaveReportExportPDFService {

    private final LeaveReportExportRepository repository;

    @Override
    public byte[] exportLeaveReportPDF(LeaveExportRequest request) {
        LocalDate now = LocalDate.now();
        LocalDate fromDate;
        LocalDate toDate;

        switch (request.getTimeRange()) {
            case THANG_NAY -> {
                fromDate = now.withDayOfMonth(1);
                toDate = now.withDayOfMonth(now.lengthOfMonth());
            }
            case QUY_NAY -> {
                int q = (now.getMonthValue() - 1) / 3;
                fromDate = LocalDate.of(now.getYear(), q * 3 + 1, 1);
                toDate = fromDate.plusMonths(3).minusDays(1);
            }
            case NAM_NAY -> {
                fromDate = LocalDate.of(now.getYear(), 1, 1);
                toDate = LocalDate.of(now.getYear(), 12, 31);
            }
            default -> throw new IllegalArgumentException("TimeRange không hợp lệ");
        }

        List<Object[]> sheet1Raw = repository.getLeaveSummaryRaw(
                fromDate, toDate,
                request.getMaPhongBan(),
                request.getTenPhongBan()
        );

        List<Object[]> sheet2Raw = repository.getLeaveDetailRaw(
                fromDate, toDate,
                request.getMaPhongBan(),
                request.getTenPhongBan()
        );

        return PDFExportUtil.exportLeaveReportPDF(sheet1Raw, sheet2Raw);
    }
}
