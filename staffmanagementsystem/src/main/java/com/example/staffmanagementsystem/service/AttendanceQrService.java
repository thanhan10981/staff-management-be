package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.AttendanceCheckInRequest;
import com.example.staffmanagementsystem.dto.QrGenerateResponse;
import com.example.staffmanagementsystem.entity.AttendanceQrToken;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.repository.AttendanceQrTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
@Service
public class AttendanceQrService {

    @Autowired
    private AttendanceQrTokenRepository qrRepo;

    @Autowired
    private AttendanceService attendanceService;

    @Transactional
    public QrGenerateResponse generateTodayQr(Long employeeId) {

        LocalDate today = LocalDate.now();
        LocalDateTime endOfDay = LocalDateTime.of(today, LocalTime.MAX);

        String token = UUID.randomUUID().toString();

        AttendanceQrToken qr = new AttendanceQrToken();

        // ⚠️ PHẢI set entity NhanVien (KHÔNG set Long)
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(employeeId.intValue()); // FIX
        qr.setNhanVien(nv);


        qr.setMaQRCode(token);
        qr.setNgayTao(today);
        qr.setTrangThai("HoatDong");
        qr.setExpiredAt(endOfDay); // transient

        qrRepo.save(qr);

        QrGenerateResponse res = new QrGenerateResponse();
        res.setToken(token);
        res.setQrPayload("ATTENDANCE:" + employeeId + ":" + today + ":" + token);
        res.setMessage("Mã QR chỉ có hiệu lực trong ngày hiện tại.");
        return res;
    }

    @Transactional
    public String scan(String token, String deviceInfo, String locationInfo) {

        AttendanceQrToken qr = qrRepo.findByMaQRCode(token)
                .orElseThrow(() ->
                        new IllegalStateException("Không thể ghi nhận chấm công. Vui lòng kiểm tra lại mã QR.")
                );

        // hết hạn: quá ngày tạo
        if (!qr.getNgayTao().equals(LocalDate.now())) {
            throw new IllegalStateException("Không thể ghi nhận chấm công. Vui lòng kiểm tra lại mã QR.");
        }

        AttendanceCheckInRequest req = new AttendanceCheckInRequest();

        try {
            // vẫn dùng user hiện tại (SecurityContext)
            return attendanceService.checkInFromFE(req);
        } catch (IllegalStateException ex) {
            return attendanceService.checkOut(deviceInfo, locationInfo);
        }
    }
}
