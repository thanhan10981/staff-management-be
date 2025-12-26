package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.NguoiDung_Quyen;
import com.example.staffmanagementsystem.entity.NguoiDungQuyenId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NguoiDungQuyenRepository extends JpaRepository<NguoiDung_Quyen, NguoiDungQuyenId> {
    @Query("select nq.maQuyen from NguoiDung_Quyen nq where nq.maNguoiDung = :maNguoiDung")
    List<Integer> findQuyenIdsByNguoiDung(Integer maNguoiDung);
    @Modifying
    @Transactional
    @Query("DELETE FROM NguoiDung_Quyen q WHERE q.maNguoiDung = :id")
    void deleteByNguoiDungId(@Param("id") Integer id);
}
