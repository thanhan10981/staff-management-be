package com.example.staffmanagementsystem.controller.schedule;

import com.example.staffmanagementsystem.dto.schedule.DayDetailScheduleDTO;

import com.example.staffmanagementsystem.service.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * Popup Chi tiết lịch làm việc theo ngày
     */
    @GetMapping("/chi-tiet")
    public List<DayDetailScheduleDTO> getChiTiet(
            @RequestParam("ngayTruc") String ngayTruc,
            @RequestParam("maKhoa") Integer maKhoa
    ) {
        return scheduleService.getChiTietTheoNgayVaKhoa(
                LocalDate.parse(ngayTruc),
                maKhoa
        );
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<byte[]> exportMonthlyPdf(
            @RequestParam Integer maKhoa,
            @RequestParam int year,
            @RequestParam int month
    ) {

        byte[] pdfBytes = scheduleService.exportMonthlyPdf(maKhoa, year, month);

        String filename = String.format(
                "lich-truc-khoa-%d-%02d-%d.pdf",
                maKhoa, month, year
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @GetMapping("/monthly-pdf")
    public ResponseEntity<byte[]> exportMonthlySchedulePdf(
            @RequestParam Integer maKhoa,
            @RequestParam int year,
            @RequestParam int month
    ) {

        byte[] pdfBytes = scheduleService.exportMonthlyPdf(maKhoa, year, month);

        String fileName = String.format(
                "lich-truc-thang-%02d-%d-khoa-%d.pdf",
                month, year, maKhoa
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        // 👇 CÁI QUAN TRỌNG NHẤT: ép browser DOWNLOAD
        headers.setContentDispositionFormData(
                "attachment",
                fileName
        );

        headers.setContentLength(pdfBytes.length);

        return new ResponseEntity<>(
                pdfBytes,
                headers,
                HttpStatus.OK
        );
    }

    @GetMapping("/print-pdf")
    public ResponseEntity<byte[]> printMonthlyPdf(
            @RequestParam Integer maKhoa,
            @RequestParam int year,
            @RequestParam int month
    ) {
        byte[] pdfBytes = scheduleService.exportMonthlyPdf(maKhoa, year, month);

        String fileName = String.format(
                "lich-truc-thang-%02d-%d-khoa-%d.pdf",
                month, year, maKhoa
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(
                HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"" + fileName + "\""
        );
        headers.setContentLength(pdfBytes.length);

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


}
