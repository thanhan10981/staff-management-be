package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.staffmanagementsystem.dto.AuditLogResponseDTO;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {

    List<AuditLog> findByMaNhanVienOrderByThoiGianDesc(Integer maNV);

    List<AuditLog> findTop10ByOrderByThoiGianDesc();

    @Query("""
        SELECT new com.example.staffmanagementsystem.dto.AuditLogResponseDTO(
            a.maLog,
            u.tenDangNhap,
            u.vaiTro,
            a.thoiGian,
            a.hanhDong,
            a.trangThai
        )
        FROM AuditLog a
        JOIN NguoiDung u ON a.nguoiThucHien = u.maNguoiDung
        ORDER BY a.thoiGian DESC
    """)
    List<AuditLogResponseDTO> findAllLogs();
}
