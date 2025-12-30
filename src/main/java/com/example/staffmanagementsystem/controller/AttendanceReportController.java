package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.AttendanceReportDetailRowDto;
import com.example.staffmanagementsystem.dto.AttendanceReportFilter;
import com.example.staffmanagementsystem.dto.AttendanceReportFilterWeb;
import com.example.staffmanagementsystem.dto.AttendanceReportSummaryDto;
import com.example.staffmanagementsystem.service.AttendanceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/attendance/report")
@CrossOrigin(origins = "http://localhost:4200")
public class AttendanceReportController {

    @Autowired
    private AttendanceReportService reportService;

    @PostMapping("/summary")
    public ResponseEntity<AttendanceReportSummaryDto> summary(@RequestBody AttendanceReportFilterWeb web) {
        LocalDate from = LocalDate.parse(web.getTuNgay());
        LocalDate to = LocalDate.parse(web.getDenNgay());
        AttendanceReportFilter f = new AttendanceReportFilter();
        f.setFromDate(from);
        f.setToDate(to);
        f.setEmployeeId(web.getMaNhanVien());
        f.setDepartmentId(web.getDepartmentId());
        f.setPositionId(web.getPositionId());
        return ResponseEntity.ok(reportService.summary(f));
    }

    @PostMapping("/detail")
    public ResponseEntity<List<AttendanceReportDetailRowDto>> detail(@RequestBody AttendanceReportFilterWeb web) {
        LocalDate from = LocalDate.parse(web.getTuNgay());
        LocalDate to = LocalDate.parse(web.getDenNgay());
        AttendanceReportFilter f = new AttendanceReportFilter();
        f.setFromDate(from);
        f.setToDate(to);
        f.setEmployeeId(web.getMaNhanVien());
        f.setDepartmentId(web.getDepartmentId());
        f.setPositionId(web.getPositionId());
        return ResponseEntity.ok(reportService.detailRows(f));
    }


    // Bổ sung export nếu cần: /export/excel, /export/pdf (dùng thư viện như Apache POI cho Excel)
}