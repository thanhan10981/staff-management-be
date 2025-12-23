package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.ThongBao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ThongBaoRepository extends JpaRepository<ThongBao, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ThongBao t WHERE t.nhanVien.maNhanVien = :id")
    void deleteByNhanVienId(@Param("id") Integer id);
}
