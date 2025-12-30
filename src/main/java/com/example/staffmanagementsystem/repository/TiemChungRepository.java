package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.TiemChung;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TiemChungRepository extends JpaRepository<TiemChung, Integer> {

    List<TiemChung> findByNhanVien_MaNhanVien(Integer maNhanVien);
    @Modifying
    @Transactional
    @Query("DELETE FROM TiemChung t WHERE t.nhanVien.maNhanVien = :id")
    void deleteByNhanVienId(@Param("id") Integer id);
}
