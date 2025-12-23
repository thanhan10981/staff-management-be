package com.example.staffmanagementsystem.repository.ThongKeBaoCaoChamCong;

import com.example.staffmanagementsystem.dto.attendancesummary.TongNghiKhongPhepDTO;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TongNghiKhongPhepRepository
        extends JpaRepository<LichTrucNgay, Integer> {

    @Query(value = """
        SELECT
            :tuNgay AS tuNgay,
            :denNgay AS denNgay,
            COUNT(lt.MaLichTruc) AS tongSoNghiKhongPhep
        FROM LichTrucNgay lt
        JOIN NhanVien nv ON nv.MaNhanVien = lt.MaNhanVien
        WHERE
            lt.NgayTruc BETWEEN :tuNgay AND :denNgay

            -- KHÔNG có chấm công
            AND NOT EXISTS (
                SELECT 1
                FROM ChamCong cc
                WHERE cc.MaLichTruc = lt.MaLichTruc
                  AND cc.ThoiGianVao IS NOT NULL
            )

            -- KHÔNG có đơn nghỉ phép hợp lệ
            AND NOT EXISTS (
                SELECT 1
                FROM DonNghiPhep dnp
                WHERE dnp.MaNhanVien = lt.MaNhanVien
                  AND lt.NgayTruc BETWEEN dnp.NgayBatDau AND dnp.NgayKetThuc
                  AND dnp.TrangThai = N'Đã duyệt'
            )

            AND (:maPhongBan IS NULL OR nv.MaPhongBan = :maPhongBan)
            AND (:maViTri IS NULL OR nv.MaViTri = :maViTri)
        """, nativeQuery = true)
    TongNghiKhongPhepDTO tinhTongNghiKhongPhep(
            @Param("tuNgay") LocalDate tuNgay,
            @Param("denNgay") LocalDate denNgay,
            @Param("maPhongBan") Integer maPhongBan,
            @Param("maViTri") Integer maViTri
    );
}
