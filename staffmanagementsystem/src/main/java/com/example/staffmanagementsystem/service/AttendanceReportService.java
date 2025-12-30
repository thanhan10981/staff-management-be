package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.AttendanceReportDetailRowDto;
import com.example.staffmanagementsystem.dto.AttendanceReportFilter;
import com.example.staffmanagementsystem.dto.AttendanceReportSummaryDto;
import com.example.staffmanagementsystem.entity.AttendanceRecord;
import com.example.staffmanagementsystem.entity.DonNghiPhep;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.entity.PhongBan;
import com.example.staffmanagementsystem.repository.AttendanceRecordRepository;
import com.example.staffmanagementsystem.repository.DonNghiPhepRepository;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.repository.PhongBanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceReportService {

    @Autowired
    private AttendanceRecordRepository recordRepo;

    @Autowired
    private DonNghiPhepRepository nghiPhepRepo;

    @Autowired
    private NhanVienRepository nhanVienRepo;

    @Autowired
    private PhongBanRepository phongBanRepo;

    @Transactional(readOnly = true)
    public AttendanceReportSummaryDto summary(AttendanceReportFilter filter) {
        // Chuyển employeeId (Long) sang String vì repository.filter nhận String
        String empIdStr = filter.getEmployeeId() != null ? String.valueOf(filter.getEmployeeId()) : null;

        List<AttendanceRecord> records = recordRepo.filter(
                filter.getFromDate(),
                filter.getToDate(),
                empIdStr);

        long workingDays = records.stream()
                .filter(r -> r.getCheckInTime() != null && r.getCheckOutTime() != null)
                .count();

        long lateCount = records.stream()
                .filter(r -> r.getStatus() != null && r.getStatus().equalsIgnoreCase("LATE"))
                .count();

        long absentCount = records.stream()
                .filter(r -> r.getStatus() != null && r.getStatus().equalsIgnoreCase("ABSENT"))
                .count();

        double onTimeRate = workingDays == 0 ? 0.0 : ((double) (workingDays - lateCount) / workingDays) * 100.0;

        AttendanceReportSummaryDto dto = new AttendanceReportSummaryDto();
        dto.setTotalWorkingDays(workingDays);
        dto.setLateCount(lateCount);
        dto.setAbsentCount(absentCount);
        dto.setOnTimeRate(Math.round(onTimeRate * 100.0) / 100.0);
        return dto;
    }

    @Transactional(readOnly = true)
    public List<AttendanceReportDetailRowDto> detailRows(AttendanceReportFilter filter) {
        String empIdStrFilter = filter.getEmployeeId() != null ? String.valueOf(filter.getEmployeeId()) : null;

        List<AttendanceRecord> records = recordRepo.filter(
                filter.getFromDate(),
                filter.getToDate(),
                empIdStrFilter);

        // Group theo maNV (String) vì MaNV trong DB là nvarchar
        Map<String, List<AttendanceRecord>> byEmp = records.stream()
                .collect(Collectors.groupingBy(AttendanceRecord::getMaNV));

        List<AttendanceReportDetailRowDto> rows = new ArrayList<>();

        for (Map.Entry<String, List<AttendanceRecord>> entry : byEmp.entrySet()) {
            String empIdString = entry.getKey();
            if (empIdString == null || empIdString.trim().isEmpty()) {
                continue; // bỏ qua bản ghi không có MaNV
            }

            List<AttendanceRecord> empRecords = entry.getValue();

            // Chuyển maNV (String) -> int (hoặc Long) tùy id của NhanVien
            int empId;
            try {
                empId = Integer.parseInt(empIdString.trim());
            } catch (NumberFormatException ex) {
                // Nếu không parse được, bỏ qua dòng này
                continue;
            }

            // Lấy thông tin nhân viên
            Optional<NhanVien> nvOpt = nhanVienRepo.findById(empId);
            NhanVien nv = nvOpt.orElse(null);

            // Lấy phòng ban: dùng trường MaPhongBan trong NhanVien (không gọi getPhongBan nếu method không tồn tại)
            PhongBan pb = null;
            try {
                if (nv != null) {
                    Integer maPhongBan = null;
                    try {
                        // Nếu NhanVien có getter getMaPhongBan()
                        maPhongBan = (Integer) NhanVien.class.getMethod("getMaPhongBan").invoke(nv);
                    } catch (NoSuchMethodException ignore) {
                        // Nếu không có getMaPhongBan, thử getPhongBan (nếu entity có relation)
                        try {
                            Object phongBanObj = NhanVien.class.getMethod("getPhongBan").invoke(nv);
                            if (phongBanObj instanceof PhongBan) {
                                pb = (PhongBan) phongBanObj;
                            }
                        } catch (Exception ignore2) {
                            // không có phương thức, pb vẫn null
                        }
                    } catch (Exception e) {
                        // ignore reflection errors, tiếp tục
                    }

                    if (pb == null && maPhongBan != null) {
                        pb = phongBanRepo.findById(maPhongBan).orElse(null);
                    }
                }
            } catch (Exception e) {
                // an toàn: nếu reflection lỗi, bỏ qua và pb = null
                pb = null;
            }

            long workingDays = empRecords.stream()
                    .filter(r -> r.getCheckInTime() != null && r.getCheckOutTime() != null)
                    .count();

            long lateCount = empRecords.stream()
                    .filter(r -> r.getStatus() != null && r.getStatus().equalsIgnoreCase("LATE"))
                    .count();

            // Đếm ngày nghỉ không lương trong khoảng thời gian (nếu repository hỗ trợ)
            long unpaidLeaveDays = 0L;
            try {
                List<DonNghiPhep> nghiPhepList = nghiPhepRepo.findByNhanVien_MaNhanVienAndNgayBatDauBetween(empId, filter.getFromDate(), filter.getToDate());
                unpaidLeaveDays = nghiPhepList.stream()
                        .filter(d -> d.getLoaiNghi() != null && d.getLoaiNghi().toLowerCase().contains("khong luong"))
                        .count();
            } catch (Exception ex) {
                // nếu method repo không tồn tại hoặc lỗi, giữ unpaidLeaveDays = 0
            }

            double onTimeRate = workingDays == 0 ? 0.0 : ((double) (workingDays - lateCount) / workingDays) * 100.0;

            AttendanceReportDetailRowDto dto = new AttendanceReportDetailRowDto();
            dto.setEmployeeId((long) empId);
            dto.setEmployeeName(nv != null ? safeGetName(nv) : ("NV " + empId));
            dto.setDepartmentName(pb != null ? safeGetDepartmentName(pb) : "");
            dto.setWorkingDays(workingDays);
            dto.setLateCount(lateCount);
            dto.setUnpaidLeaveDays(unpaidLeaveDays);
            dto.setOnTimeRate(Math.round(onTimeRate * 100.0) / 100.0);
            dto.setRiskColor(colorByOnTimeRate(onTimeRate));
            rows.add(dto);
        }

        // Optional: sắp xếp theo tên
        rows.sort(Comparator.comparing(AttendanceReportDetailRowDto::getEmployeeName, Comparator.nullsLast(String::compareTo)));
        return rows;
    }

    private String colorByOnTimeRate(double onTimeRate) {
        if (onTimeRate >= 90) return "GREEN";
        if (onTimeRate >= 75) return "YELLOW";
        return "RED";
    }

    // Helper an toàn lấy tên nhân viên (tránh gọi method không tồn tại)
    private String safeGetName(NhanVien nv) {
        try {
            return (String) NhanVien.class.getMethod("getTenNhanVien").invoke(nv);
        } catch (Exception e) {
            return null;
        }
    }

    // Helper an toàn lấy tên phòng ban
    private String safeGetDepartmentName(PhongBan pb) {
        try {
            return (String) PhongBan.class.getMethod("getTenPhongBan").invoke(pb);
        } catch (Exception e) {
            return null;
        }
    }
}
