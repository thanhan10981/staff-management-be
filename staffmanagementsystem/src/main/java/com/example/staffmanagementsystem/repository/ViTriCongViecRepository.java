package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.ViTriCongViec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViTriCongViecRepository extends JpaRepository<ViTriCongViec, Integer> {
}
