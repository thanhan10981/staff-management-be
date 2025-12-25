package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.PhongBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhongBanRepository extends JpaRepository<PhongBan, Integer> {
    List<PhongBan> findByKhoa_Id(int maKhoa);
}
