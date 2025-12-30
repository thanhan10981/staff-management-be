package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.DonNghiPhep;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DonNghiPhepRepository extends JpaRepository<DonNghiPhep, Integer> {

    // =====================================================
    //  DELETE THEO NHÂN VIÊN
    //  DÙNG CHUNG CHO TẤT CẢ CÁC MÀN
    // =====================================================
    @Modifying
    @Transactional
    @Query("DELETE FROM DonNghiPhep d WHERE d.nhanVien.maNhanVien = :nhanVienId")
    void deleteByNhanVienId(@Param("nhanVienId") Integer nhanVienId);

    // =====================================================
    //  LẤY ĐƠN NGHỈ THEO NHÂN VIÊN + KHOẢNG NGÀY
    //  (TRANG KHÁC TRONG HỆ THỐNG)
    // =====================================================
    List<DonNghiPhep> findByNhanVien_MaNhanVienAndNgayBatDauBetween(
            Integer empId,
            LocalDate fromDate,
            LocalDate toDate
    );

    // =====================================================
    //  SEARCH (TRANG CỦA BẠN)
    // =====================================================
    @Query("""
        SELECT d FROM DonNghiPhep d
        WHERE LOWER(d.lyDo) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(d.loaiNghi) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR CAST(d.nhanVien.maNhanVien AS string) LIKE CONCAT('%', :keyword, '%')
    """)
    List<DonNghiPhep> search(@Param("keyword") String keyword);

}
