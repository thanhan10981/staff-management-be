package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.AuditLogDTO;
import com.example.staffmanagementsystem.entity.AuditLog;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.repository.AuditLogRepository;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditRepo;
    private final NhanVienRepository nhanVienRepo;

    @Override
    public List<AuditLogDTO> getLogsByEmployee(Integer maNV) {

        List<AuditLog> list = auditRepo.findByMaNhanVienOrderByThoiGianDesc(maNV);

        return list.stream().map(log -> {
            NhanVien nv = nhanVienRepo.findById(log.getNguoiThucHien()).orElse(null);

            return new AuditLogDTO(
                    log.getHanhDong(),
                    log.getMoTa(),
                    log.getThoiGian(),
                    nv != null ? nv.getTenNhanVien() : "Hệ thống",
                    log.getTrangThai()
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> getRecentActivities() {
        return auditRepo.findTop10ByOrderByThoiGianDesc();
    }


    @Override
    public void logLogin(Integer maNguoiDung,
                         Integer maNhanVien,
                         String tenDangNhap) {

        AuditLog log = AuditLog.builder()
                .nguoiThucHien(maNguoiDung)
                .maNhanVien(maNhanVien)
                .hanhDong("LOGIN")
                .moTa("User " + tenDangNhap + " đăng nhập thành công")
                .trangThai("ThanhCong")
                .thoiGian(LocalDateTime.now())
                .build();

        auditRepo.save(log);
    }

    @Override
    public void logLoginFail(String tenDangNhap) {

        AuditLog log = AuditLog.builder()
                .hanhDong("LOGIN")
                .moTa("User " + tenDangNhap + " đăng nhập thất bại")
                .trangThai("ThatBai")     // ⭐ NEW
                .thoiGian(LocalDateTime.now())
                .build();

        auditRepo.save(log);
    }

    @Override
    public void logUpdateProfile(
            Integer maNguoiDung,
            Integer maNhanVien,
            String moTa,
            String trangThai
    ) {
        AuditLog log = AuditLog.builder()
                .nguoiThucHien(maNguoiDung)
                .maNhanVien(maNhanVien)
                .hanhDong("UPDATE_PROFILE")
                .moTa(moTa)
                .thoiGian(LocalDateTime.now())
                .trangThai(trangThai)
                .build();

        auditRepo.save(log);
    }

}
