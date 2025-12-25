package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.AttendanceCheckInRequest;
import com.example.staffmanagementsystem.dto.AttendanceHistoryDto;
import com.example.staffmanagementsystem.entity.AttendanceActionLog;
import com.example.staffmanagementsystem.entity.AttendanceRecord;
import com.example.staffmanagementsystem.entity.CaLamViec;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import com.example.staffmanagementsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRecordRepository recordRepo;

    @Autowired
    private AttendanceActionLogRepository logRepo;

    @Autowired
    private LichTrucNgayRepository lichTrucRepo;

    @Autowired
    private CaLamViecRepository caLamViecRepo;

    @Autowired
    private NhanVienRepository nhanVienRepo;

    // TODO: Nếu bạn muốn realtime, inject AttendanceUpdatePublisher và bỏ comment 2 dòng publish
    // @Autowired
    // private AttendanceUpdatePublisher publisher;

    // Helper: lấy MaNhanVien hiện tại từ security context (bạn tự triển khai thực tế)
    private Long currentEmployeeId() {
        // TODO: Lấy từ JWT / SecurityContext
        return 1L; // tạm để compile
    }

    private String timeText(LocalDateTime t) {
        return t != null ? t.format(DateTimeFormatter.ofPattern("HH:mm")) : "";
    }

    @Transactional
    public String checkInFromFE(AttendanceCheckInRequest req) {

        AttendanceRecord rec = new AttendanceRecord();

        rec.setMaNV(req.getMaNV());
        rec.setStatus(req.getTrangThai());
        rec.setCheckInTime(req.getThoiGianVao());
        rec.setCheckOutTime(req.getThoiGianRa());
        rec.setDeviceInfo(req.getThietBi());
        rec.setMaQR(req.getMaQR());
        // nếu có lịch trực
        if (req.getMaLichTruc() != null) {
            LichTrucNgay lich = lichTrucRepo
                    .findById(req.getMaLichTruc())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch trực"));
            rec.setLichTrucNgay(lich);
        }

        recordRepo.save(rec);

        return "Chấm công thành công";
    }


    @Transactional
    public String checkOut(String deviceInfo, String locationInfo) {
        Long empIdLong = currentEmployeeId();
        String empIdStr = String.valueOf(empIdLong);
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        AttendanceRecord rec = recordRepo.findByEmployeeIdAndWorkDate(empIdStr, today)
                .orElseThrow(() -> new IllegalStateException("Bạn chưa chấm công giờ vào hôm nay."));

        if (rec.getCheckOutTime() != null) {
            throw new IllegalStateException("Bạn đã chấm công giờ ra rồi.");
        }

        // Lấy ca để tính về sớm (nếu có)
        LichTrucNgay lich = rec.getLichTrucNgay();
        if (lich == null) {
            throw new IllegalStateException("Không tìm thấy lịch trực.");
        }
        CaLamViec ca = caLamViecRepo.findById(lich.getMaCa())
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy ca làm việc."));

        LocalTime gioKetThuc = ca.getGioKetThuc();
        if (gioKetThuc != null && now.toLocalTime().isBefore(gioKetThuc.minusMinutes(15))) {
            rec.setStatus("EARLY");
        }

        rec.setCheckOutTime(now);

        String currentDevice = rec.getDeviceInfo() != null ? rec.getDeviceInfo() : "";
        rec.setDeviceInfo(currentDevice + " | Out: " + (deviceInfo != null ? deviceInfo : "") + " | " + (locationInfo != null ? locationInfo : ""));

        // Tính tổng phút trước khi save
        rec.computeTotalMinutes();

        recordRepo.save(rec);

        // Log
        AttendanceActionLog log = new AttendanceActionLog();
        log.setActorUserId(Math.toIntExact(empIdLong));
        log.setAction("CHECK_OUT");
        log.setDetails("Chấm công ra lúc " + timeText(now) + " - NV: " + empIdStr);
        log.setCreatedAt(LocalDateTime.now());
        log.setEmployeeId(Math.toIntExact(empIdLong));
        logRepo.save(log);

        return "Giờ ra đã được ghi nhận lúc " + timeText(now) + ".";
    }

    @Transactional(readOnly = true)
    public List<AttendanceHistoryDto> historyForCurrentEmployee() {
        Long empId = currentEmployeeId();
        String empIdStr = String.valueOf(empId);

        List<AttendanceRecord> records = recordRepo.findByEmployeeIdOrderByWorkDateDesc(empIdStr);

        return records.stream().map(r -> {
            AttendanceHistoryDto dto = new AttendanceHistoryDto();
            // workDate: lấy từ checkInTime nếu có, ngược lại từ checkOutTime
            dto.setWorkDate(r.getCheckInTime() != null ? r.getCheckInTime().toLocalDate()
                    : (r.getCheckOutTime() != null ? r.getCheckOutTime().toLocalDate() : null));
            dto.setCheckIn(r.getCheckInTime());
            dto.setCheckOut(r.getCheckOutTime());
            // đảm bảo totalMinutes đã được tính (nếu chưa, compute)
            if (r.getTotalMinutes() == null) {
                r.computeTotalMinutes();
            }
            dto.setTotalMinutes(r.getTotalMinutes());
            dto.setStatus(r.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }

    // Giữ method getHistory() nếu controller gọi
    @Transactional(readOnly = true)
    public List<AttendanceHistoryDto> getHistory() {
        return historyForCurrentEmployee();
    }

}