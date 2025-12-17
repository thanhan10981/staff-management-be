package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {

    List<AuditLog> findByMaNhanVienOrderByThoiGianDesc(Integer maNV);
    List<AuditLog> findTop10ByOrderByThoiGianDesc();
}
