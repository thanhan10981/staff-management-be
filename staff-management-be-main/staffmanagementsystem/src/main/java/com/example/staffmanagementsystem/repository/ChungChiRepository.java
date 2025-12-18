package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.ChungChi;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChungChiRepository extends JpaRepository<ChungChi, Integer> {

    List<ChungChi> findByNhanVien_MaNhanVien(Integer maNhanVien);
    @Modifying
    @Transactional
    @Query("DELETE FROM ChungChi c WHERE c.nhanVien.maNhanVien = :id")
    void deleteByNhanVienId(@Param("id") Integer id);
}
