package com.example.staffmanagementsystem.repository.leaveoverview;

import com.example.staffmanagementsystem.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LeaveUnpaidStatisticRepository
        extends JpaRepository<NhanVien, Integer> {

    @Query(value = """
        WITH DonHopLe AS (
            SELECT
                dnp.MaNhanVien,
                dnp.NgayBatDau,
                dnp.NgayKetThuc
            FROM DonNghiPhep dnp
            WHERE dnp.TrangThai = N'Đã duyệt'
        ),

        NgayNghi AS (
            SELECT
                dh.MaNhanVien,
                DATEADD(DAY, v.number, dh.NgayBatDau) AS NgayNghi
            FROM DonHopLe dh
            JOIN master..spt_values v
                ON v.type = 'P'
               AND v.number <= DATEDIFF(DAY, dh.NgayBatDau, dh.NgayKetThuc)
        ),

        NgayNghiDanhSo AS (
            SELECT
                nn.MaNhanVien,
                nn.NgayNghi,
                ROW_NUMBER() OVER (
                    PARTITION BY nn.MaNhanVien
                    ORDER BY nn.NgayNghi
                ) AS ThuTuNgayNghi
            FROM NgayNghi nn
            WHERE YEAR(nn.NgayNghi) = YEAR(:today)
        ),

        NgayKhongLuong AS (
            SELECT
                MaNhanVien,
                NgayNghi
            FROM NgayNghiDanhSo
            WHERE ThuTuNgayNghi > 12
        )

        SELECT COUNT(*)
        FROM NgayKhongLuong nkl
        JOIN NhanVien nv
            ON nv.MaNhanVien = nkl.MaNhanVien
        JOIN PhongBan pb
            ON pb.MaPhongBan = nv.MaPhongBan
        WHERE nkl.NgayNghi BETWEEN :fromDate AND :toDate
          AND (:maPhongBan IS NULL OR pb.MaPhongBan = :maPhongBan)
          AND (:tenPhongBan IS NULL OR pb.TenPhongBan = :tenPhongBan)
        """, nativeQuery = true)
    Long getTotalUnpaidLeaveDays(
            @Param("today") LocalDate today,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("maPhongBan") Integer maPhongBan,
            @Param("tenPhongBan") String tenPhongBan
    );
}
