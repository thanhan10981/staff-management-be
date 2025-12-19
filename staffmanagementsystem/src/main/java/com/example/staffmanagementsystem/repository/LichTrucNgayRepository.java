package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.LichTrucNgay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

}