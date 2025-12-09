package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.CauHinhCaTruc_Phong;
import com.example.staffmanagementsystem.entity.CauHinhId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CauHinhCaTruc_PhongRepository extends JpaRepository<CauHinhCaTruc_Phong, CauHinhId> {
}