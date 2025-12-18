package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.AuditLogDTO;
import com.example.staffmanagementsystem.entity.AuditLog;
import com.example.staffmanagementsystem.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AuditLogService {
    List<AuditLogDTO> getLogsByEmployee(Integer maNV);

    List<AuditLog> getRecentActivities();
}
