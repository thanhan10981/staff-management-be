package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.LuongPhuCap;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LuongPhuCapRepository extends JpaRepository<LuongPhuCap, Integer> {

    @Query("SELECT lp FROM LuongPhuCap lp WHERE lp.luongThang.maLuong = :maLuong")
    List<LuongPhuCap> findByLuongThang(@Param("maLuong") int maLuong);
    @Modifying
    @Transactional
    @Query("DELETE FROM LuongPhuCap l WHERE l.luongThang.nhanVien.maNhanVien = :id")
    void deleteByNhanVienId(@Param("id") Integer id);
}
