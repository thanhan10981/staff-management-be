package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.dto.ShiftOption;
import com.example.staffmanagementsystem.entity.CaLamViec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CaLamViecRepository extends JpaRepository<CaLamViec, Integer> {
    @Query("""
        SELECT 
            c.maCa AS maCa,
            c.tenCa AS tenCa
        FROM CaLamViec c
    """)
    List<ShiftOption> findCaMuonDoi();
}
