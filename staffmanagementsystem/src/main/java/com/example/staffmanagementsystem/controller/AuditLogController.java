package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.staffmanagementsystem.dto.AuditLogResponseDTO;
import java.util.List;

@RestController
@RequestMapping("/api/AuditLog")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping("/nhanvien/{maNV}")
    public ResponseEntity<?> getAuditLogs(@PathVariable Integer maNV) {
        var result = auditLogService.getLogsByEmployee(maNV);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/recent")
    public ResponseEntity<?> getRecentLogs() {
        return ResponseEntity.ok(auditLogService.getRecentActivities());
    }

    @GetMapping
    public List<AuditLogResponseDTO> getAllLogs() {
        return auditLogService.getAllLogs();
    }
}
