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

    @Modifying
    @Transactional
    @Query("DELETE FROM DonNghiPhep t WHERE t.nhanVien.maNhanVien = :id")
    void deleteByNhanVienId(@Param("id") Integer id);

    // SỬA CHÍNH TẠI ĐÂY: dùng nhanVien.maNhanVien thay vì maNhanVien trực tiếp
    List<DonNghiPhep> findByNhanVien_MaNhanVienAndNgayBatDauBetween(Integer empId, LocalDate fromDate, LocalDate toDate);

    // HOẶC CÁCH AN TOÀN NHẤT (khuyến nghị để không lỗi nữa):
    /*
    @Query("SELECT d FROM DonNghiPhep d " +
           "WHERE d.nhanVien.maNhanVien = :empId " +
           "AND d.ngayBatDau BETWEEN :fromDate AND :toDate")
    List<DonNghiPhep> findByMaNhanVienAndNgayBatDauBetween(@Param("empId") Integer empId,
                                                           @Param("fromDate") LocalDate fromDate,
                                                           @Param("toDate") LocalDate toDate);
    */
}