package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.dto.QrGenerateResponse;
import com.example.staffmanagementsystem.dto.QrScanRequest;
import com.example.staffmanagementsystem.service.AttendanceQrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/attendance/qr")
@CrossOrigin(origins = "http://localhost:4200")
public class AttendanceQrController {

    @Autowired
    private AttendanceQrService qrService;

    @PostMapping("/generate/{employeeId}")
    public ResponseEntity<QrGenerateResponse> generate(@PathVariable Long employeeId) {
        return ResponseEntity.ok(qrService.generateTodayQr(employeeId));
    }

    @PostMapping("/scan")
    public ResponseEntity<Map<String, String>> scan(@RequestBody QrScanRequest req) {
        try {
            String token = req.getToken() != null ? req.getToken() : req.getMaQRCode();
            String msg = qrService.scan(token, req.getDeviceInfo(), req.getLocationInfo());
            return ResponseEntity.ok(Map.of("message", msg));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Không thể ghi nhận chấm công. Vui lòng kiểm tra lại mã QR hoặc kết nối mạng."));
        }
    }

}