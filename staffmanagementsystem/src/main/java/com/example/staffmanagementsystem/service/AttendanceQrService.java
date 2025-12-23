package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.AttendanceCheckInRequest;
import com.example.staffmanagementsystem.dto.QrGenerateResponse;
import com.example.staffmanagementsystem.entity.AttendanceQrToken;
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
        qr.setEmployeeId((long) employeeId.intValue()); // nếu entity dùng Integer
        qr.setToken(token);
        qr.setCreatedAt(today);
        qr.setStatus("HoatDong");
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
        AttendanceQrToken qr = qrRepo.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Không thể ghi nhận chấm công. Vui lòng kiểm tra lại mã QR."));

        // Kiểm tra hạn (createdAt + 1 ngày)
        if (qr.getCreatedAt().plusDays(1).isBefore(LocalDate.now())) {
            throw new IllegalStateException("Không thể ghi nhận chấm công. Vui lòng kiểm tra lại mã QR.");
        }
        AttendanceCheckInRequest req = new AttendanceCheckInRequest();
        // Nếu muốn override currentEmployeeId() bằng employeeId trong QR:
        Long qrEmployeeId = qr.getEmployeeId() != null ? Long.valueOf(qr.getEmployeeId()) : null;

        // Tạm thời: nếu qrEmployeeId khác null, set context tạm thời (cách đơn giản: thay đổi currentEmployeeId() implementation)
        // Nếu currentEmployeeId() lấy từ SecurityContext, bạn cần implement logic xác thực token -> set Authentication.
        // Ở đây giữ cách cũ: gọi attendanceService.checkIn/checkOut cho user hiện tại.
        try {
            // Nếu bạn muốn check-in cho qrEmployeeId thay vì current user, bạn cần implement method checkInForEmployee(...)
            return attendanceService.checkInFromFE(req);
        } catch (IllegalStateException ex) {
            return attendanceService.checkOut(deviceInfo, locationInfo);
        }
    }

}