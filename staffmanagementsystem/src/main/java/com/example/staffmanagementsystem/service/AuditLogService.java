package com.example.staffmanagementsystem.service;

import com.example.staffmanagementsystem.dto.AuditLogDTO;
import com.example.staffmanagementsystem.entity.AuditLog;
import java.util.List;
import com.example.staffmanagementsystem.dto.AuditLogResponseDTO;

public interface AuditLogService {

    List<AuditLogDTO> getLogsByEmployee(Integer maNV);

    List<AuditLog> getRecentActivities();

    List<AuditLogResponseDTO> getAllLogs();
}
