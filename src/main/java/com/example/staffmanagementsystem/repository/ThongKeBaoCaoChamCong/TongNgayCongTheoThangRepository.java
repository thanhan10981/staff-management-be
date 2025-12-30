package com.example.staffmanagementsystem.repository.ThongKeBaoCaoChamCong;

import com.example.staffmanagementsystem.dto.attendancesummary.TongNgayCongTheoThangDTO;
import com.example.staffmanagementsystem.entity.ChamCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TongNgayCongTheoThangRepository
        extends JpaRepository<ChamCong, Integer> {

    @Query(value = """
    WITH ThangTrongNam AS (
        SELECT 1 AS thang UNION ALL
        SELECT 2 UNION ALL
        SELECT 3 UNION ALL
        SELECT 4 UNION ALL
        SELECT 5 UNION ALL
        SELECT 6 UNION ALL
        SELECT 7 UNION ALL
        SELECT 8 UNION ALL
        SELECT 9 UNION ALL
        SELECT 10 UNION ALL
        SELECT 11 UNION ALL
        SELECT 12
    )
    SELECT
        t.thang AS thang,
        COUNT(cc.MaChamCong) AS tongNgayCong
    FROM ThangTrongNam t
    LEFT JOIN ChamCong cc
           ON MONTH(cc.ThoiGianVao) = t.thang
          AND YEAR(cc.ThoiGianVao) = :nam
    LEFT JOIN LichTrucNgay lt
           ON lt.MaLichTruc = cc.MaLichTruc
    LEFT JOIN NhanVien nv
           ON nv.MaNhanVien = lt.MaNhanVien
          AND (:maPhongBan IS NULL OR nv.MaPhongBan = :maPhongBan)
          AND (:maViTri IS NULL OR nv.MaViTri = :maViTri)
    GROUP BY t.thang
    ORDER BY t.thang
    """, nativeQuery = true)
    List<TongNgayCongTheoThangDTO> tongNgayCongTheoNam(
            @Param("nam") Integer nam,
            @Param("maPhongBan") Integer maPhongBan,
            @Param("maViTri") Integer maViTri
    );

}
