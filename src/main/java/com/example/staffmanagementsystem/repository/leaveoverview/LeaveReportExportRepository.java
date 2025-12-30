package com.example.staffmanagementsystem.repository.leaveoverview;

import com.example.staffmanagementsystem.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveReportExportRepository extends JpaRepository<NhanVien, Integer> {

    // Sheet 1
    @Query(value = """
DECLARE @RangeStart DATE = :fromDate;
DECLARE @RangeEnd   DATE = :toDate;

WITH DonHopLe AS (
    SELECT
        dnp.MaNhanVien,
        dnp.LoaiNghi,
        dnp.NgayBatDau,
        dnp.NgayKetThuc
    FROM DonNghiPhep dnp
    WHERE dnp.TrangThai = N'Đã duyệt'
),

NgayNghi AS (
    SELECT
        dh.MaNhanVien,
        dh.LoaiNghi,
        DATEADD(DAY, v.number, dh.NgayBatDau) AS NgayNghi
    FROM DonHopLe dh
    JOIN master..spt_values v
        ON v.type = 'P'
       AND v.number <= DATEDIFF(DAY, dh.NgayBatDau, dh.NgayKetThuc)
),

-- Tổng nghỉ từ đầu năm (để tính phép & vượt)
TongNghiNam AS (
    SELECT
        MaNhanVien,
        COUNT(*) AS TongNgayNghiNam
    FROM NgayNghi
    WHERE YEAR(NgayNghi) = YEAR(@RangeEnd)
    GROUP BY MaNhanVien
),

-- Nghỉ trong range export
TongNghiTrongRange AS (
    SELECT
        MaNhanVien,
        SUM(CASE WHEN LoaiNghi = N'Nghỉ phép năm' THEN 1 ELSE 0 END) AS NghiPhepNam,
        SUM(CASE WHEN LoaiNghi = N'Nghỉ ốm' THEN 1 ELSE 0 END) AS NghiBenh,
        COUNT(*) AS TongNgayTrongRange
    FROM NgayNghi
    WHERE NgayNghi BETWEEN @RangeStart AND @RangeEnd
    GROUP BY MaNhanVien
),

-- Nghỉ không lương vượt 12 ngày (tính đến cuối range)
KhongLuong AS (
    SELECT
        MaNhanVien,
        CASE
            WHEN COUNT(*) > 12 THEN COUNT(*) - 12
            ELSE 0
        END AS TongKhongLuongDenCuoiRange
    FROM NgayNghi
    WHERE NgayNghi <= @RangeEnd
      AND YEAR(NgayNghi) = YEAR(@RangeEnd)
    GROUP BY MaNhanVien
)

SELECT
    nv.HoTen       AS tenNhanVien,
    nv.Email       AS email,
    pb.TenPhongBan AS tenPhongBan,

    ISNULL(r.NghiPhepNam, 0) AS tongNghiPhepNam,
    ISNULL(r.NghiBenh, 0)    AS tongNghiBenh,

    CASE
        WHEN kl.TongKhongLuongDenCuoiRange > 0
        THEN
            CASE
                WHEN kl.TongKhongLuongDenCuoiRange > ISNULL(r.TongNgayTrongRange, 0)
                THEN ISNULL(r.TongNgayTrongRange, 0)
                ELSE kl.TongKhongLuongDenCuoiRange
            END
        ELSE 0
    END AS nghiKhongLuongVuot,

    ISNULL(n.TongNgayNghiNam, 0) AS tongNgayNghi,

    CASE
        WHEN 12 - ISNULL(n.TongNgayNghiNam, 0) > 0
        THEN 12 - ISNULL(n.TongNgayNghiNam, 0)
        ELSE 0
    END AS soNgayConLai

FROM NhanVien nv
JOIN PhongBan pb ON nv.MaPhongBan = pb.MaPhongBan
LEFT JOIN TongNghiNam n ON n.MaNhanVien = nv.MaNhanVien
LEFT JOIN TongNghiTrongRange r ON r.MaNhanVien = nv.MaNhanVien
LEFT JOIN KhongLuong kl ON kl.MaNhanVien = nv.MaNhanVien

WHERE (:maPhongBan IS NULL OR pb.MaPhongBan = :maPhongBan)
  AND (:tenPhongBan IS NULL OR pb.TenPhongBan = :tenPhongBan)

ORDER BY nv.HoTen
""", nativeQuery = true)
    List<Object[]> getLeaveSummaryRaw(
            LocalDate fromDate,
            LocalDate toDate,
            Integer maPhongBan,
            String tenPhongBan
    );

    // =======================
    // Sheet 2: GIỮ NGUYÊN
    // =======================
    @Query(value = """
Select nv.MaNhanVien, nv.HoTen, nv.Email, pb.TenPhongBan,
       dnp.LoaiNghi, dnp.NgayBatDau, dnp.NgayKetThuc,
       dnp.LyDo, dnp.MaDon
from NhanVien nv
join PhongBan pb on nv.MaPhongBan = pb.MaPhongBan
join DonNghiPhep dnp on dnp.MaNhanVien = nv.MaNhanVien
WHERE (:maPhongBan IS NULL OR pb.MaPhongBan = :maPhongBan)
  AND (:tenPhongBan IS NULL OR pb.TenPhongBan = :tenPhongBan)
  AND dnp.NgayBatDau BETWEEN :fromDate AND :toDate
""", nativeQuery = true)
    List<Object[]> getLeaveDetailRaw(
            LocalDate fromDate,
            LocalDate toDate,
            Integer maPhongBan,
            String tenPhongBan
    );
}