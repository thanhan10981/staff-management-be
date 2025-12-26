package com.example.staffmanagementsystem.repository.ThongKeBaoCaoChamCong;

import com.example.staffmanagementsystem.dto.attendancesummary.TongLanDiTreDTO;
import com.example.staffmanagementsystem.entity.ChamCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TongLanDiTreRepository
        extends JpaRepository<ChamCong, Integer> {

    @Query(value = """
        SELECT
            :tuNgay AS tuNgay,
            :denNgay AS denNgay,
            COUNT(cc.MaChamCong) AS tongSoLanDiTre
        FROM ChamCong cc
        JOIN LichTrucNgay lt ON lt.MaLichTruc = cc.MaLichTruc
        JOIN CaLamViec ca    ON ca.MaCa = lt.MaCa
        JOIN NhanVien nv    ON nv.MaNhanVien = lt.MaNhanVien
        WHERE
            cc.ThoiGianVao IS NOT NULL
            AND CAST(cc.ThoiGianVao AS DATE)
                BETWEEN :tuNgay AND :denNgay
            AND CAST(cc.ThoiGianVao AS TIME) > ca.GioBatDau
            AND (:maPhongBan IS NULL OR nv.MaPhongBan = :maPhongBan)
            AND (:maViTri IS NULL OR nv.MaViTri = :maViTri)
        """, nativeQuery = true)
    TongLanDiTreDTO tinhTongLanDiTre(
            @Param("tuNgay") LocalDate tuNgay,
            @Param("denNgay") LocalDate denNgay,
            @Param("maPhongBan") Integer maPhongBan,
            @Param("maViTri") Integer maViTri
    );
}
