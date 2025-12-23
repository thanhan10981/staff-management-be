package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.dto.attendancesummary.ChiTietChamCongDTO;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChiTietChamCongRepository extends JpaRepository<LichTrucNgay, Integer> {

    @Query(value = """
        WITH ChamCongDauTien AS (
            SELECT
                cc.MaNV,
                cc.MaLichTruc,
                CAST(cc.ThoiGianVao AS DATE) AS ngayCong,
                MIN(cc.ThoiGianVao) AS gioVaoDauTien
            FROM ChamCong cc
            WHERE CAST(cc.ThoiGianVao AS DATE)
                  BETWEEN :tuNgay AND :denNgay
            GROUP BY
                cc.MaNV,
                cc.MaLichTruc,
                CAST(cc.ThoiGianVao AS DATE)
        )
        SELECT
            nv.HoTen        AS tenNhanVien,
            nv.Email        AS email,
            pb.TenPhongBan  AS tenPhongBan,
            vt.TenViTri     AS tenViTri,
            lt.NgayTruc     AS ngayCong,

            CASE WHEN ccdt.gioVaoDauTien IS NOT NULL THEN 1 ELSE 0 END AS coDiLam,

            CASE
                WHEN ccdt.gioVaoDauTien IS NOT NULL
                 AND CAST(ccdt.gioVaoDauTien AS TIME) > ca.GioBatDau
                THEN DATEDIFF(
                    MINUTE,
                    ca.GioBatDau,
                    CAST(ccdt.gioVaoDauTien AS TIME)
                )
                ELSE 0
            END AS diTre,

            CASE
                WHEN ccdt.gioVaoDauTien IS NULL
                 AND NOT EXISTS (
                    SELECT 1
                    FROM DonNghiPhep dnp
                    WHERE dnp.MaNhanVien = lt.MaNhanVien
                      AND lt.NgayTruc BETWEEN dnp.NgayBatDau AND dnp.NgayKetThuc
                      AND dnp.TrangThai = N'Đã duyệt'
                 )
                THEN 1 ELSE 0
            END AS nghiKhongPhep,

            CASE
                WHEN ccdt.gioVaoDauTien IS NULL
                 AND EXISTS (
                    SELECT 1
                    FROM DonNghiPhep dnp
                    WHERE dnp.MaNhanVien = lt.MaNhanVien
                      AND lt.NgayTruc BETWEEN dnp.NgayBatDau AND dnp.NgayKetThuc
                      AND dnp.TrangThai = N'Đã duyệt'
                 )
                THEN 1 ELSE 0
            END AS nghiCoPhep

        FROM LichTrucNgay lt
        JOIN NhanVien nv      ON nv.MaNhanVien = lt.MaNhanVien
        JOIN PhongBan pb      ON pb.MaPhongBan = nv.MaPhongBan
        JOIN ViTriCongViec vt ON vt.MaViTri    = nv.MaViTri
        JOIN CaLamViec ca     ON ca.MaCa       = lt.MaCa

        LEFT JOIN ChamCongDauTien ccdt
               ON ccdt.MaNV = lt.MaNhanVien
              AND ccdt.MaLichTruc = lt.MaLichTruc
              AND ccdt.ngayCong = lt.NgayTruc

        WHERE
            lt.NgayTruc BETWEEN :tuNgay AND :denNgay
            AND (:maPhongBan IS NULL OR pb.MaPhongBan = :maPhongBan)
            AND (:maViTri IS NULL OR vt.MaViTri = :maViTri)

        ORDER BY lt.NgayTruc, nv.HoTen
        """, nativeQuery = true)
    List<ChiTietChamCongDTO> chiTietChamCong(
            @Param("tuNgay") LocalDate tuNgay,
            @Param("denNgay") LocalDate denNgay,
            @Param("maPhongBan") Integer maPhongBan,
            @Param("maViTri") Integer maViTri
    );
}
