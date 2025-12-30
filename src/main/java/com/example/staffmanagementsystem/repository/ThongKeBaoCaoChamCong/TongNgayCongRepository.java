package com.example.staffmanagementsystem.repository.ThongKeBaoCaoChamCong;

import com.example.staffmanagementsystem.dto.attendancesummary.TongNgayCongDTO;
import com.example.staffmanagementsystem.entity.ChamCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TongNgayCongRepository
        extends JpaRepository<ChamCong, Integer> {

    @Query(value = """
            SELECT
                :tuNgay AS tuNgay,
                :denNgay AS denNgay,
                COUNT(cc.MaChamCong) AS tongSoNgayCong
            FROM ChamCong cc
            LEFT JOIN LichTrucNgay lt ON lt.MaLichTruc = cc.MaLichTruc
            LEFT JOIN NhanVien nv     ON nv.MaNhanVien = lt.MaNhanVien
            WHERE
                cc.ThoiGianVao IS NOT NULL
                AND CAST(cc.ThoiGianVao AS DATE)
                    BETWEEN :tuNgay AND :denNgay
                AND (
                    :maPhongBan IS NULL
                    OR nv.MaPhongBan = :maPhongBan
                )
                AND (
                    :maViTri IS NULL
                    OR nv.MaViTri = :maViTri
                )

        """, nativeQuery = true)
    TongNgayCongDTO tinhTongNgayCong(
            @Param("tuNgay") LocalDate tuNgay,
            @Param("denNgay") LocalDate denNgay,
            @Param("maPhongBan") Integer maPhongBan,
            @Param("maViTri") Integer maViTri
    );
}
