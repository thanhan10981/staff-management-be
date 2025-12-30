package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    Optional<NguoiDung> findByTenDangNhap(String tenDangNhap);
    @Query("SELECT u FROM NguoiDung u WHERE u.maNhanVien = :id")
    NguoiDung findByNhanVienId(@Param("id") Integer id);

    @Query("""
        SELECT u.maNhanVien
        FROM NguoiDung u
        WHERE u.maNguoiDung = :maNguoiDung
    """)
    Optional<Integer> findMaNhanVienByMaNguoiDung(
            @Param("maNguoiDung") Integer maNguoiDung
    );
}
