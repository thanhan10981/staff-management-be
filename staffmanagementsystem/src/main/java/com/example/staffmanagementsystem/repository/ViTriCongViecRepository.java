package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.dto.ViTriCongViecDTO;
import com.example.staffmanagementsystem.entity.ViTriCongViec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViTriCongViecRepository extends JpaRepository<ViTriCongViec, Integer> {

    @Query("""
    SELECT new com.example.staffmanagementsystem.dto.ViTriCongViecDTO(
        v.id,
        v.tenViTri
    )
    FROM ViTriCongViec v
    WHERE v.phongBan.id = :maPhongBan
""")
    List<ViTriCongViecDTO> findByMaPhongBan(@Param("maPhongBan") Integer maPhongBan);

}
