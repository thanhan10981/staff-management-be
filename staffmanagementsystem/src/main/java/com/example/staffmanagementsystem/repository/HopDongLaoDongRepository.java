package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.HopDongLaoDong;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HopDongLaoDongRepository extends JpaRepository<HopDongLaoDong, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM HopDongLaoDong h WHERE h.nhanVien.maNhanVien = :id")
    void deleteByNhanVienId(@Param("id") Integer id);
}
