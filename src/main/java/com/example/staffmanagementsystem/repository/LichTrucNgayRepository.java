package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.dto.schedule.DayDetailScheduleDTO;
import com.example.staffmanagementsystem.dto.schedule.MonthlyScheduleRowDTO;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LichTrucNgayRepository extends JpaRepository<LichTrucNgay, Integer> {

    List<LichTrucNgay> findByNgayTruc(LocalDate date);

    List<LichTrucNgay> findByNgayTrucBetween(LocalDate from, LocalDate to);

    List<LichTrucNgay> findByNhanVien_MaNhanVien(Integer maNV);

    List<LichTrucNgay> findByMaPhongAndNgayTrucBetween(Integer maPhong, LocalDate from, LocalDate to);

    List<LichTrucNgay> findByMaPhongAndMaCaAndNgayTruc(Integer maPhong, Integer maCa, LocalDate date);

    // CŨ – giữ nguyên
    List<LichTrucNgay> findByPhongVatLy_Khoa_IdAndNgayTrucBetween(
            Integer id,
            LocalDate start,
            LocalDate end
    );

    // ⭐ MỚI – BẮT BUỘC PHẢI CÓ
    List<LichTrucNgay> findByNhanVien_Khoa_IdAndNgayTrucBetween(
            Integer maKhoa,
            LocalDate start,
            LocalDate end
    );

    @Query("""
        SELECT l.maCa AS ca, COUNT(l) AS soNguoi
        FROM LichTrucNgay l
        WHERE l.ngayTruc = :date
          AND l.phongVatLy.khoa.id = :maKhoa
        GROUP BY l.maCa
        ORDER BY l.maCa
    """)
    List<Map<String, Object>> countByNgayAndKhoa(
            @Param("maKhoa") Integer maKhoa,
            @Param("date") LocalDate date
    );

    boolean existsByNhanVien_MaNhanVienAndNgayTruc(Integer maNV, LocalDate date);

    List<LichTrucNgay> findByNhanVien_MaNhanVienAndNgayTrucBetween(
            Integer maNhanVien,
            LocalDate start,
            LocalDate end
    );
    @Query(value = """
        WITH CC AS (
            SELECT
                MaLichTruc,
                MIN(ThoiGianVao) AS ThoiGianVao,
                MAX(ThoiGianRa)  AS ThoiGianRa
            FROM ChamCong
            GROUP BY MaLichTruc
        )
        SELECT
            lt.MaLichTruc              AS maLichTruc,
            nv.AnhDaiDien              AS anhDaiDien,
            nv.HoTen                   AS hoTen,
            vt.TenViTri                AS tenViTri,
            pvl.TenPhong               AS tenPhong,
            k.TenKhoa                  AS tenKhoa,
            ca.TenCa                   AS tenCa,
            CAST(
                DATEDIFF(MINUTE, ca.GioBatDau, ca.GioKetThuc) / 60.0
                AS DECIMAL(5,2)
            )                           AS tongGioLam,
            CASE
                WHEN cc.MaLichTruc IS NULL
                    THEN N'Chưa làm'
                WHEN cc.ThoiGianVao IS NOT NULL
                     AND cc.ThoiGianRa IS NULL
                    THEN N'Đang làm'
                WHEN cc.ThoiGianVao IS NOT NULL
                     AND cc.ThoiGianRa IS NOT NULL
                    THEN N'Đã kết thúc ca'
                ELSE N'Không xác định'
            END                         AS trangThai
        FROM LichTrucNgay lt
        JOIN CaLamViec ca ON ca.MaCa = lt.MaCa
        LEFT JOIN NhanVien nv ON nv.MaNhanVien = lt.MaNhanVien
        LEFT JOIN ViTriCongViec vt ON vt.MaViTri = nv.MaViTri
        LEFT JOIN PhongVatLy pvl ON pvl.MaPhong = lt.MaPhong
        LEFT JOIN Khoa k ON k.MaKhoa = pvl.MaKhoa
                         AND k.MaKhoa = :maKhoa
        LEFT JOIN CC cc ON cc.MaLichTruc = lt.MaLichTruc
        WHERE lt.NgayTruc = :ngayTruc
        ORDER BY ca.MaCa, lt.MaLichTruc
        """,
            nativeQuery = true)
    List<DayDetailScheduleDTO> findChiTietTheoNgayVaKhoa(
            @Param("ngayTruc") LocalDate ngayTruc,
            @Param("maKhoa") Integer maKhoa
    );

    Optional<LichTrucNgay> findByNhanVien_MaNhanVienAndNgayTruc(
            Integer maNhanVien,
            LocalDate ngayTruc
    );

    @Query("""
    SELECT l FROM LichTrucNgay l
    WHERE (:from IS NULL OR l.ngayTruc >= :from)
      AND (:to IS NULL OR l.ngayTruc <= :to)

      AND (:maKhoa IS NULL OR l.nhanVien.khoa.id = :maKhoa)
      AND (:maPhong IS NULL OR l.maPhong = :maPhong)
      AND (:maNhanVien IS NULL OR l.nhanVien.maNhanVien = :maNhanVien)
      AND (:maViTri IS NULL OR l.nhanVien.viTriCongViec.id = :maViTri)
""")
    List<LichTrucNgay> filterLichTruc(
            @Param("maKhoa") Integer maKhoa,
            @Param("maPhong") Integer maPhong,
            @Param("maViTri") Integer maViTri,
            @Param("maNhanVien") Integer maNhanVien,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    @Query(value = """
    WITH CC AS (
        SELECT
            MaLichTruc,
            MIN(ThoiGianVao) AS ThoiGianVao,
            MAX(ThoiGianRa)  AS ThoiGianRa
        FROM ChamCong
        GROUP BY MaLichTruc
    )
    SELECT
        lt.NgayTruc                AS ngayTruc,
        nv.HoTen                   AS hoTen,
        vt.TenViTri                AS tenViTri,
        pvl.TenPhong               AS tenPhong,
        ca.TenCa                   AS tenCa,
        CAST(
            DATEDIFF(MINUTE, ca.GioBatDau, ca.GioKetThuc) / 60.0
            AS DECIMAL(5,2)
        )                           AS tongGioLam,
        CASE
            WHEN cc.MaLichTruc IS NULL THEN N'Chưa làm'
            WHEN cc.ThoiGianVao IS NOT NULL AND cc.ThoiGianRa IS NULL THEN N'Đang làm'
            WHEN cc.ThoiGianVao IS NOT NULL AND cc.ThoiGianRa IS NOT NULL THEN N'Đã kết thúc ca'
            ELSE N'Không xác định'
        END AS trangThai
    FROM LichTrucNgay lt
    JOIN CaLamViec ca ON ca.MaCa = lt.MaCa
    JOIN NhanVien nv ON nv.MaNhanVien = lt.MaNhanVien
    JOIN ViTriCongViec vt ON vt.MaViTri = nv.MaViTri
    JOIN PhongVatLy pvl ON pvl.MaPhong = lt.MaPhong
    LEFT JOIN CC cc ON cc.MaLichTruc = lt.MaLichTruc
    WHERE pvl.MaKhoa = :maKhoa
      AND lt.NgayTruc BETWEEN :from AND :to
    ORDER BY lt.NgayTruc, ca.MaCa
""", nativeQuery = true)
    List<MonthlyScheduleRowDTO> getMonthlySchedule(
            @Param("maKhoa") Integer maKhoa,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    boolean existsByNhanVien_MaNhanVienAndMaCaAndNgayTruc(
            Integer maNhanVien,
            Integer maCa,
            LocalDate ngayTruc
    );

    Optional<LichTrucNgay> findFirstByNhanVien_MaNhanVienAndMaCaAndNgayTruc(
            Integer maNhanVien,
            Integer maCa,
            LocalDate ngayTruc
    );

}