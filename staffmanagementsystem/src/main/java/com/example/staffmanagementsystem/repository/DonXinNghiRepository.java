package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.DonNghiPhep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonXinNghiRepository extends JpaRepository<DonNghiPhep, Integer> {

    @Query(value = """
    SELECT 
        COUNT(DISTINCT MaNhanVien) * 100.0 /
        (SELECT COUNT(*) FROM NhanVien)
    FROM DonNghiPhep
    WHERE MONTH(NgayBatDau) = :thang
      AND YEAR(NgayBatDau) = :nam
      AND TrangThai = N'Đã duyệt'
""", nativeQuery = true)
    Double tiLeNghiPhep(@Param("thang") int thang,
                        @Param("nam") int nam);

    @Query(value = """
    SELECT TOP 1
        MONTH(NgayBatDau),
        YEAR(NgayBatDau)
    FROM DonNghiPhep
    WHERE TrangThai = N'Đã duyệt'
    ORDER BY NgayBatDau DESC
""", nativeQuery = true)
    Object findLatestMonthYear();



}
