package com.example.staffmanagementsystem.service.leaveoverview;

import com.example.staffmanagementsystem.dto.leaveoverview.LeaveExportRequest;

public interface LeaveReportExportService {
    byte[] exportLeaveReport(LeaveExportRequest request);
}
