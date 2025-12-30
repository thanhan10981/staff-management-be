package com.example.staffmanagementsystem.repository.leaveoverview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LeaveTotalStatisticRepository
        extends JpaRepository<com.example.staffmanagementsystem.entity.NhanVien, Integer> {

    @Query(value = """
        WITH DonHopLe AS (
            SELECT
                dnp.MaNhanVien,
                dnp.NgayBatDau,
                dnp.NgayKetThuc
            FROM DonNghiPhep dnp
            WHERE dnp.TrangThai = N'Đã duyệt'
              AND dnp.LoaiNghi = N'Nghỉ phép năm'
        ),

        NgayNghi AS (
            SELECT
                dh.MaNhanVien,
                DATEADD(DAY, v.number, dh.NgayBatDau) AS NgayNghi
            FROM DonHopLe dh
            JOIN master..spt_values v
                ON v.type = 'P'
               AND v.number <= DATEDIFF(DAY, dh.NgayBatDau, dh.NgayKetThuc)
        )

        SELECT COUNT(*) 
        FROM NgayNghi nn
        JOIN NhanVien nv ON nv.MaNhanVien = nn.MaNhanVien
        JOIN PhongBan pb ON nv.MaPhongBan = pb.MaPhongBan
        WHERE nn.NgayNghi BETWEEN :fromDate AND :toDate
          AND (:maPhongBan IS NULL OR pb.MaPhongBan = :maPhongBan)
          AND (:tenPhongBan IS NULL OR pb.TenPhongBan = :tenPhongBan)
        """, nativeQuery = true)
    Integer getTongNgayNghiPhepNam(
            LocalDate fromDate,
            LocalDate toDate,
            Integer maPhongBan,
            String tenPhongBan
    );
}
