package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.dto.BangLuongNhanVienDTO;
import com.example.staffmanagementsystem.dto.KpiLuongDTO;
import com.example.staffmanagementsystem.dto.QuyLuongPhongBanDto;
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
    // ✅ LẤY THÁNG/NĂM MỚI NHẤT
    @Query(
            value = """
        SELECT TOP 1 Thang, Nam
        FROM LuongThang
        ORDER BY Nam DESC, Thang DESC
    """,
            nativeQuery = true
    )
    List<Object[]> findLatestMonthYear();




    @Query(
            value = """
        SELECT
            COALESCE(SUM(LuongCoBan),0),
            COALESCE(SUM(PhuCapCoDinh + PhuCapTrucCa + PhuCapKhac),0),
            COALESCE(SUM(BHXH),0),
            COALESCE(SUM(ThueTNCN),0),
            COALESCE(SUM(ThucLanh),0)
        FROM LuongThang
        WHERE Thang = :thang
          AND Nam   = :nam
    """,
            nativeQuery = true
    )
    List<Object[]> tongHopLuongTheoThang(int thang, int nam);
    @Query("""
    SELECT new com.example.staffmanagementsystem.dto.QuyLuongPhongBanDto(
        k.tenKhoa,
        SUM(lt.thucLanh)
    )
    FROM LuongThang lt
    JOIN lt.nhanVien nv
    JOIN nv.khoa k
    GROUP BY k.tenKhoa
""")
    List<QuyLuongPhongBanDto> tinhQuyLuongTheoPhongBan();
    @Query("""
SELECT
    SUM(lt.luongCoBan),
    SUM(lt.phuCapCoDinh + lt.phuCapTrucCa + lt.phuCapKhac),
    SUM(lt.bhxh),
    SUM(lt.thueTncn)
FROM LuongThang lt
JOIN lt.nhanVien nv
JOIN nv.khoa k
WHERE (:month IS NULL OR lt.thang = :month)
AND (:year IS NULL OR lt.nam = :year)
AND (:dept IS NULL OR k.id = :dept)
""")
    List<Object[]> coCauLuongBaoCao(
            @Param("month") Integer month,
            @Param("year") Integer year,
            @Param("dept") Integer dept
    );

    @Query("""
SELECT new com.example.staffmanagementsystem.dto.QuyLuongPhongBanDto(
    k.tenKhoa,
    SUM(lt.thucLanh)
)
FROM LuongThang lt
JOIN lt.nhanVien nv
JOIN nv.khoa k
WHERE (:dept IS NULL OR k.id = :dept)
AND (:year IS NULL OR lt.nam = :year)
AND (:month IS NULL OR lt.thang = :month)
AND (:role IS NULL OR nv.viTriCongViec = :role)
GROUP BY k.tenKhoa
""")
    List<QuyLuongPhongBanDto> filterQuyLuong(
            @Param("dept") Integer dept,
            @Param("month") Integer month,
            @Param("year") Integer year,
            @Param("role") String role
    );

    @Query("""

            SELECT new com.example.staffmanagementsystem.dto.BangLuongNhanVienDTO(
     nv.tenNhanVien,
     k.tenKhoa,
     CAST(lt.luongCoBan AS double),
     CAST((lt.phuCapCoDinh + lt.phuCapTrucCa + lt.phuCapKhac) AS double),
     CAST(lt.phuCapCoDinh AS double),
     CAST(lt.thucLanh AS double)
 )
 
FROM LuongThang lt
JOIN lt.nhanVien nv
JOIN nv.khoa k
WHERE (:dept IS NULL OR k.id = :dept)
AND (:role IS NULL OR nv.viTriCongViec = :role)
AND (:month IS NULL OR lt.thang = :month)
AND (:year IS NULL OR lt.nam = :year)
""")
    List<BangLuongNhanVienDTO> filterBangLuongNhanVien(
            @Param("dept") Integer dept,
            @Param("role") String role,
            @Param("month") Integer month,
            @Param("year") Integer year
    );

    @Query("""
SELECT new com.example.staffmanagementsystem.dto.KpiLuongDTO(
    SUM(lt.thucLanh),
    SUM(lt.luongCoBan),
    SUM(lt.phuCapCoDinh + lt.phuCapTrucCa + lt.phuCapKhac),
    SUM(lt.phuCapCoDinh)
)
FROM LuongThang lt
JOIN lt.nhanVien nv
JOIN nv.khoa k
WHERE (:dept IS NULL OR k.id = :dept)
AND (:role IS NULL OR nv.viTriCongViec = :role)
AND (:month IS NULL OR lt.thang = :month)
AND (:year IS NULL OR lt.nam = :year)
""")
    KpiLuongDTO thongKeKpiLuong(
            Integer dept,
            String role,
            Integer month,
            Integer year
    );

    @Query("""
SELECT
  SUM(CASE WHEN lt.thang = :thang AND lt.nam = :nam THEN lt.thucLanh ELSE 0 END),
  SUM(CASE WHEN lt.thang = :thangTruoc AND lt.nam = :namTruoc THEN lt.thucLanh ELSE 0 END)
FROM LuongThang lt
""")
    Object[] thongKeQuyLuongThang(
            int thang,
            int nam,
            int thangTruoc,
            int namTruoc
    );





}
