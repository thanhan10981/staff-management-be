package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.dto.ApprovalDTO;
import com.example.staffmanagementsystem.entity.DonNghiPhep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<DonNghiPhep, Integer> {

    // ===== NGHỈ PHÉP =====
    @Query("""
    SELECT new com.example.staffmanagementsystem.dto.ApprovalDTO(
        nv.maNhanVien,
        nv.tenNhanVien,
        'NghiPhep',
        CONCAT(d.ngayBatDau, ' - ', d.ngayKetThuc),
        d.loaiNghi,
        d.lyDo,
        d.id
    )
    FROM DonNghiPhep d
    JOIN d.nhanVien nv
    WHERE d.trangThai = 'Cho duyet'
""")
    List<ApprovalDTO> getPendingLeaveApprovals();

}
