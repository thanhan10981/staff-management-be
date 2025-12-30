package com.example.staffmanagementsystem.repository.ThongKeBaoCaoChamCong;

import com.example.staffmanagementsystem.dto.attendancesummary.TyLeDiTreTheoPhongBanDTO;
import com.example.staffmanagementsystem.entity.ChamCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TyLeDiTreRepository extends JpaRepository<ChamCong, Integer> {

    @Query(value = """
        SELECT 
            pb.TenPhongBan AS tenPhongBan,
            COUNT(cc.MaChamCong) AS soLanDiTre
        FROM ChamCong cc
        JOIN LichTrucNgay lt ON lt.MaLichTruc = cc.MaLichTruc
        JOIN CaLamViec ca    ON ca.MaCa = lt.MaCa
        JOIN NhanVien nv    ON nv.MaNhanVien = lt.MaNhanVien
        JOIN PhongBan pb    ON pb.MaPhongBan = nv.MaPhongBan
        WHERE cc.ThoiGianVao IS NOT NULL
          AND CAST(cc.ThoiGianVao AS DATE) BETWEEN :tuNgay AND :denNgay
          AND CAST(cc.ThoiGianVao AS TIME) > ca.GioBatDau
        GROUP BY pb.TenPhongBan
        ORDER BY pb.TenPhongBan
        """, nativeQuery = true)
    List<TyLeDiTreTheoPhongBanDTO> tyLeDiTre(
            LocalDate tuNgay,
            LocalDate denNgay
    );
}
