package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.AuditLogDTO;
import com.example.staffmanagementsystem.dto.AuditLogResponseDTO;
import com.example.staffmanagementsystem.entity.AuditLog;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.repository.AuditLogRepository;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditRepo;
    private final NhanVienRepository nhanVienRepo;
    private final AuditLogRepository auditLogRepository;

    @Override
    public List<AuditLogDTO> getLogsByEmployee(Integer maNV) {

        List<AuditLog> list = auditRepo.findByMaNhanVienOrderByThoiGianDesc(maNV);

        return list.stream().map(log -> {
            NhanVien nv = nhanVienRepo.findById(log.getNguoiThucHien()).orElse(null);

            return new AuditLogDTO(
                    log.getHanhDong(),
                    log.getMoTa(),
                    log.getThoiGian(),
                    nv != null ? nv.getTenNhanVien() : "Hệ thống"
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<AuditLog> getRecentActivities() {
        return auditRepo.findTop10ByOrderByThoiGianDesc();
    }

    @Override
    public List<AuditLogResponseDTO> getAllLogs() {
        return auditLogRepository.findAllLogs();
    }
}
