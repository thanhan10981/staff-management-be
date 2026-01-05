package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.ChamCong;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ChamCongRepository extends JpaRepository<ChamCong, Integer> {

    Optional<ChamCong> findByMaNVAndLichTrucNgay_NgayTruc(
            String maNV,
            LocalDate ngayTruc
    );
}
