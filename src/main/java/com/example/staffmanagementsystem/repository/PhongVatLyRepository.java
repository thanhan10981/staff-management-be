package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.PhongVatLy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhongVatLyRepository extends JpaRepository<PhongVatLy, Integer> {
    List<PhongVatLy> findByKhoa_Id(int maKhoa);
}
