package com.example.staffmanagementsystem.repository.ThongKeBaoCaoChamCong;

import com.example.staffmanagementsystem.dto.attendancesummary.TiLeDungGioDTO;
import com.example.staffmanagementsystem.entity.ChamCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TiLeDungGioRepository extends JpaRepository<ChamCong, Integer> {

    @Query(value = """
        SELECT
            :tuNgay AS tuNgay,
            :denNgay AS denNgay,
            
            -- Số lần đi làm đúng giờ
            COUNT(CASE WHEN CAST(cc.ThoiGianVao AS TIME) <= ca.GioBatDau THEN 1 END) AS soLanDungGio,
            
            -- Tổng số lần đi làm
            COUNT(cc.MaChamCong) AS tongSoLanDiLam

        FROM ChamCong cc
        JOIN LichTrucNgay lt ON lt.MaLichTruc = cc.MaLichTruc
        JOIN CaLamViec ca    ON ca.MaCa = lt.MaCa
        JOIN NhanVien nv    ON nv.MaNhanVien = lt.MaNhanVien
        WHERE cc.ThoiGianVao IS NOT NULL
          AND CAST(cc.ThoiGianVao AS DATE) BETWEEN :tuNgay AND :denNgay
          AND (:maPhongBan IS NULL OR nv.MaPhongBan = :maPhongBan)
          AND (:maViTri IS NULL OR nv.MaViTri = :maViTri)
        """, nativeQuery = true)
    TiLeDungGioDTO tinhTiLeDungGio(
            @Param("tuNgay") LocalDate tuNgay,
            @Param("denNgay") LocalDate denNgay,
            @Param("maPhongBan") Integer maPhongBan,
            @Param("maViTri") Integer maViTri
    );
}
