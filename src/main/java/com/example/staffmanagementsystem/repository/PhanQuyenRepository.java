package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.PhanQuyen;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PhanQuyenRepository extends JpaRepository<PhanQuyen, Integer> {
    Optional<PhanQuyen> findByTenQuyen(String tenQuyen);
}
