package com.example.staffmanagementsystem.service.leaveoverview;

import com.example.staffmanagementsystem.dto.leaveoverview.LeaveExportRequest;

public interface LeaveReportExportPDFService {
    byte[] exportLeaveReportPDF(LeaveExportRequest request);
}
