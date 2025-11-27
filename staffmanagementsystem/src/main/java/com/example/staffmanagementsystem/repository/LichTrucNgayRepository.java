package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.LichTrucNgay;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
public interface LichTrucNgayRepository extends JpaRepository<LichTrucNgay, Integer> {

    List<LichTrucNgay> findByNgayTrucBetween(LocalDate start, LocalDate end);

    List<LichTrucNgay> findByNhanVien_MaNhanVienAndNgayTruc(Integer maNhanVien, LocalDate ngayTruc);

    List<LichTrucNgay> findByNhanVien_MaNhanVienAndNgayTrucBetween(Integer maNhanVien, LocalDate start, LocalDate end);

    List<LichTrucNgay> findByNgayTruc(LocalDate ngayTruc);

    // Nếu vẫn muốn giữ tên này:
    @Query("SELECT l FROM LichTrucNgay l WHERE l.nhanVien.maNhanVien = :id AND l.ngayTruc = :day")
    List<LichTrucNgay> findByEmpAndDate(@Param("id") Integer id, @Param("day") LocalDate day);
    @Modifying
    @Transactional
    @Query("DELETE FROM LichTrucNgay l WHERE l.nhanVien.maNhanVien = :id")
    void deleteByNhanVienId(@Param("id") Integer id);
}
