package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.ChamCong;
import com.example.staffmanagementsystem.entity.QRChamCong;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface QRChamCongRepository extends JpaRepository<QRChamCong, Integer> {

    List<QRChamCong> findByNhanVien_MaNhanVien(Integer maNhanVien);

    @Modifying
    @Transactional
    @Query("DELETE FROM QRChamCong q WHERE q.nhanVien.maNhanVien = :id")
    void deleteByNhanVienId(@Param("id") Integer id);


    Optional<QRChamCong> findByMaQRCodeAndTrangThai(String maQRCode, String trangThai);
}
