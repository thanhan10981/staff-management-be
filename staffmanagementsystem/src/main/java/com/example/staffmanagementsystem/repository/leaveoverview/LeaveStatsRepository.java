package com.example.staffmanagementsystem.repository.leaveoverview;

import com.example.staffmanagementsystem.entity.DonNghiPhep;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface LeaveStatsRepository extends Repository<DonNghiPhep, Long> {

    @Query(value = """
        WITH DonHopLe AS (
            SELECT
                dnp.MaNhanVien,
                dnp.NgayBatDau,
                dnp.NgayKetThuc
            FROM DonNghiPhep dnp
            WHERE dnp.TrangThai = N'Đã duyệt'
              AND dnp.LoaiNghi = N'Nghỉ ốm'
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
    Long getTotalSickLeaveDays(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("maPhongBan") Integer maPhongBan,
            @Param("tenPhongBan") String tenPhongBan
    );
}
