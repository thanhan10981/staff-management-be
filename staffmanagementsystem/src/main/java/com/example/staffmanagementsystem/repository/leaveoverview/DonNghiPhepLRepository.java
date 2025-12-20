package com.example.staffmanagementsystem.repository.leaveoverview;

import com.example.staffmanagementsystem.entity.DonNghiPhep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonNghiPhepLRepository extends JpaRepository<DonNghiPhep, Long> {

    @Query("""
        SELECT DISTINCT d.loaiNghi
        FROM DonNghiPhep d
        WHERE d.loaiNghi IS NOT NULL
          AND TRIM(d.loaiNghi) <> ''
    """)
    List<String> findDistinctLoaiNghi();
}
