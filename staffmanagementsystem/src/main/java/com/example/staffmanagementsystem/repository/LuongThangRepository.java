package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.LuongThang;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LuongThangRepository extends JpaRepository<LuongThang, Integer> {

    // Lấy lương tháng gần nhất của 1 nhân viên
    @Query("SELECT l FROM LuongThang l WHERE l.nhanVien.maNhanVien = :maNV ORDER BY l.nam DESC, l.thang DESC")
    List<LuongThang> getLatestByNhanVien(@Param("maNV") Integer maNV);

    @Query("SELECT lt FROM LuongThang lt WHERE lt.thang = :month AND lt.nam = :year")
    List<LuongThang> findByMonthYear(@Param("month") int month, @Param("year") int year);
    Optional<LuongThang> findByNhanVien_MaNhanVienAndThangAndNam(
            Integer maNhanVien, Integer thang, Integer nam);
    @Query("""
    SELECT lt FROM LuongThang lt
    WHERE (:dept IS NULL OR :dept = 0 OR lt.nhanVien.khoa.id = :dept)
      AND (lt.thang BETWEEN :fromMonth AND :toMonth)
      AND (lt.nam BETWEEN :fromYear AND :toYear)
      AND (:keyword IS NULL 
          OR lt.nhanVien.tenNhanVien LIKE %:keyword% 
          OR lt.nhanVien.email LIKE %:keyword%)
""")
    List<LuongThang> filterSalary(
            @Param("dept") Integer department,
            @Param("fromMonth") int fromMonth,
            @Param("fromYear") int fromYear,
            @Param("toMonth") int toMonth,
            @Param("toYear") int toYear,
            @Param("keyword") String keyword
    );
    @Modifying
    @Transactional
    @Query("DELETE FROM LuongThang l WHERE l.nhanVien.maNhanVien = :id")
    void deleteByNhanVienId(@Param("id") Integer id);
}
