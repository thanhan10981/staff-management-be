package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.PhanCongCaTruc;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PhanCongCaTrucRepository extends JpaRepository<PhanCongCaTruc, Integer> {
    List<PhanCongCaTruc> findByMaKhoa(Integer maKhoa);
    List<PhanCongCaTruc> findByNhanVien_MaNhanVien(Integer maNhanVien);


}