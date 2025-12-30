package com.example.staffmanagementsystem.controller.leaveoverview;

import com.example.staffmanagementsystem.dto.leaveoverview.LeaveExportRequest;
import com.example.staffmanagementsystem.service.leaveoverview.LeaveReportExportPDFService;
import com.example.staffmanagementsystem.service.leaveoverview.LeaveReportExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/leave-report")
@RequiredArgsConstructor
public class LeaveReportExportController {

    private final LeaveReportExportService service;
    private final LeaveReportExportPDFService pdfService;

    @PostMapping("/exportExcel")
    public ResponseEntity<byte[]> export(@RequestBody LeaveExportRequest request) {
        byte[] file = service.exportLeaveReport(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=bao_cao_nghi_phep_" + LocalDate.now().getMonthValue() + ".xlsx"
                )
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @PostMapping("/exportPDF")
    public ResponseEntity<byte[]> exportPDF(@RequestBody LeaveExportRequest request) {
        byte[] file = pdfService.exportLeaveReportPDF(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=bao_cao_nghi_phep.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }
}
